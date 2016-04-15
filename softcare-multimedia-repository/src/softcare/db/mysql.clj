;;
;; Using java.jdbc: http://clojure-doc.org/articles/ecosystem/java_jdbc/home.html
;;
(ns softcare.db.mysql
    (:use [softcare.globals]
          [softcare.db.schemas])
    (:import com.mchange.v2.c3p0.ComboPooledDataSource)
    (:require [clojure.java.jdbc :as sql]
              [softcare.logs.logs :as log]
              [softcare.config :as config]
              [crypto.password.bcrypt :as password]))

;; CONSTANTS - DEFs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Connection
(def ^:private db-conn {:classname config/get-mysql-classname
                        :subprotocol config/get-mysql-subprotocol
                        :subname config/get-mysql-subname
                        :user config/get-mysql-username
                        :password config/get-mysql-password})

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Connection pool
(defn- pool
    [spec]
    (let [cpds (doto (ComboPooledDataSource.)
                   (.setDriverClass (:classname spec))
                   (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
                   (.setUser (:user spec))
                   (.setPassword (:password spec))
                   ;; expire excess connections after 30 minutes of inactivity:
                   (.setMaxIdleTimeExcessConnections (* 30 60))
                   ;; expire connections after 3 hours of inactivity:
                   (.setMaxIdleTime (* 3 60 60)))]
        {:datasource cpds}))

;; Function:
(def ^:private pooled-db (delay (pool db-conn)))

;; Function:
(defn- db-connection [] @pooled-db)

;; Function:
(defn- db-exists?
    "Check whether a given table exists."
    [table-name]
    (try
        (do (sql/query (db-connection) (str "select * from " table-name))
            true)
        (catch Throwable ex
            false)))

;; MYSQL FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; QUERIES / TRANSACTIONS
;; Function: login: (do-login "rsucasas" "password" "12")
(defn do-login
    [username password ip]
    (let [res (sql/query (db-connection) ["SELECT * FROM users WHERE username = ?" username])]
        (if-not (empty? res)
            (if (password/check password ((first res) :password))
                (gen-response CODE_OK (map #(dissoc (into {} %) :password) res) "jwt" ip)
                (gen-response CODE_ERROR {:message "no users found - not valid username / password (1)"}))
            (gen-response CODE_ERROR {:message "no users found - not valid username / password (2)"}))))

;; Function: get all: (get-all "users")
(defn get-all
    [coll-name]
    (case coll-name
        "users" (for [x (sql/query (db-connection) "select * from users")]
                    (dissoc x :password))
        "documents" (sql/query (db-connection) "select * from documents")))

;; Function: get records / info from all documents
(defn get-records-info []
    (let [res (sql/query (db-connection) "select _id, type from documents")
          video (count (filter (fn [x] (= (x :type) "video")) res))
          music (count (filter (fn [x] (= (x :type) "music")) res))
          image (count (filter (fn [x] (= (x :type) "image")) res))
          book (count (filter (fn [x] (= (x :type) "book")) res))
          other (count (filter (fn [x] (= (x :type) "other")) res))]
        (gen-response CODE_OK
                      {:video video :music music :image image :book book :other other})))

;; Function: get elements from collection / table by field value: (get-by-id "documents" 1)
(defn get-by-id
    [coll-name id]
    (case coll-name
        "users"     (if (>= ((first (sql/query (db-connection) ["SELECT COUNT(*) AS t FROM users WHERE _id = ?" id])) :t) 1)
                        (gen-response
                            CODE_OK
                            (dissoc (first (sql/query (db-connection) ["SELECT * FROM users WHERE _id = ?" id])) :password))
                        (gen-response CODE_ERROR {:message "warning: no users found"}))
        "documents" (if (>= ((first (sql/query (db-connection) ["SELECT COUNT(*) AS t FROM documents WHERE _id = ?" id])) :t) 1)
                        (gen-response
                            CODE_OK
                            (first (sql/query (db-connection) ["SELECT * FROM documents WHERE _id = ?" id])))
                        (gen-response CODE_ERROR {:message "warning: no documents found"}))))

;; Function: get by type (documents) or rol (users): (get-all-by-type "documents" {:type "video"})
(defn get-all-by-field
    [coll-name map-values]
    (case coll-name
        "users"     (gen-response CODE_OK
                                  (let [type-value (map-values :rol)]
                                      (for [x (sql/query (db-connection) ["SELECT * FROM users WHERE rol = ?" type-value])]
                                          (dissoc x :password))))
        "documents" (gen-response CODE_OK
                                  (let [type-value (map-values :type)]
                                      (sql/query (db-connection) ["SELECT * FROM documents WHERE type = ?" type-value])))))

;; Function: (get-all-records "documents")
(defn get-all-records
    [coll-name]
    (case coll-name
        "users" (gen-response CODE_OK
                              (for [x (sql/query (db-connection) ["SELECT * FROM users"])]
                                      (dissoc x :password)))
        "documents" (gen-response CODE_OK
                                  (sql/query (db-connection) ["SELECT * FROM documents"]))))

;; Function: insert new element
(defn- insert
    [coll-name doc-map error-msg]
    (if-not (empty? (sql/insert! (db-connection) coll-name doc-map))
        (gen-response CODE_OK (get-all coll-name))
        (gen-response CODE_ERROR {:message (str "error: " error-msg)})))

;; Function: insert new document
(defn insert-new
    [coll-name body]
    (case coll-name
        "users"     (let [doc-map (user-schema-instance body "new" "")]
                        (if (= ((first (sql/query (db-connection) ["SELECT COUNT(*) AS t FROM users WHERE username = ?" (get doc-map :username)])) :t) 0)
                            (insert "users" doc-map "user was not created")
                            (gen-response CODE_ERROR {:message "error: user already exists"})))
        "documents" (let [doc-map (document-schema-instance body "new")]
                        (if (= ((first (sql/query (db-connection) ["SELECT COUNT(*) AS t FROM documents WHERE name = ?" (get doc-map :name)])) :t) 0)
                            (insert "documents" doc-map "video was not uploaded")
                            (gen-response CODE_ERROR {:message "error: (video) name already exists"})))))

;; Function: update element
(defn- update-elem
    [coll-name id data error-msg]
    (if (>= (first (sql/update! (db-connection) coll-name data ["_id = ?" id])) 1)
        (gen-response CODE_OK (get-all coll-name))
        (gen-response CODE_ERROR (str "error: " error-msg))))

;; Function:
(defn update-by-id
    [coll-name id body]
    (case coll-name
        "users"     (update-elem "users" id (user-schema-instance body "update" id) "no users found")
        "documents" (update-elem "documents" id (document-schema-instance body "update") "no documents found")))

;; Function: delete element
(defn- delete
    [coll-name id error-msg]
    (if (>= (first (sql/delete! (db-connection) coll-name ["_id = ?" id])) 1)
        (gen-response CODE_OK (get-all coll-name))
        (gen-response CODE_ERROR {:message (str "error: " error-msg)})))

;; Function:
(defn delete-by-id
    [coll-name id]
    (case coll-name
        "users"     (delete coll-name id "no users found")
        "documents" (delete coll-name id "no documents found")))

;; INITIALIZE DATABASE
;; Function: delete all and add new data to new collections: (initialize)
(defn initialize
    []
    (do
        (log/debug "Initializing database: " config/get-mysql-subname " ...")
        ;; users
        (when (db-exists? "users")
            (sql/db-do-commands (db-connection) (sql/drop-table-ddl :users)))
        (sql/db-do-commands (db-connection)
                            (sql/create-table-ddl :users
                                                  [:_id "bigint" "auto_increment" "primary key"]
                                                  [:username "varchar(128)"]
                                                  [:name "varchar(128)"]
                                                  [:password "varchar(256)"]
                                                  [:rol "varchar(65)"]
                                                  [:location "varchar(64)"]
                                                  [:created_at "varchar(128)"]
                                                  [:updated_at "varchar(128)"]))
        ;; multimedia
        (when (db-exists? "documents")
            (sql/db-do-commands (db-connection) (sql/drop-table-ddl :documents)))
        (sql/db-do-commands (db-connection)
                            (sql/create-table-ddl :documents
                                                  [:_id "bigint" "auto_increment" "primary key"]
                                                  [:name "varchar(128)"]
                                                  [:desc1 "varchar(256)"]
                                                  [:url "varchar(128)"]
                                                  [:type "varchar(64)"]
                                                  [:tags "varchar(128)"]
                                                  [:stored "varchar(16)"]
                                                  [:created_at "varchar(128)"]
                                                  [:updated_at "varchar(128)"]))
        ;; initial data
        (sql/insert! (db-connection) :users SUPER-ADMIN-USER)
        (sql/insert! (db-connection) :users DEFAULT-ADMIN-USER)
        (sql/insert! (db-connection) :users DEFAULT-ADMIN-USER2)
        (sql/insert! (db-connection) :documents DEFAULT-DOCUMENT)
        (gen-response CODE_OK {:message "database initialized"})))
