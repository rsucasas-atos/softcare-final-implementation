(ns softcare.db.mongodb
    (:use [softcare.globals]
          [softcare.db.schemas])
    (:require [monger.core :as mg]
              [monger.credentials :as mcred]
              [monger.collection :as mc]
              [cheshire.core :refer [generate-string]]
              [cheshire.generate :refer [add-encoder encode-str]]
              [softcare.config :as config]
              [softcare.logs.logs :as log]
              [softcare.utilities.utils :as utils]
              [crypto.password.bcrypt :as password])
    (:import [org.bson.types ObjectId])
    (:import [com.mongodb MongoOptions ServerAddress]))

;; changes behaviour of cheshire/generate-string method ==> http://siscia.github.io/2014/08/28/manage-mongodbs-objectid-and-json/
(add-encoder org.bson.types.ObjectId (fn [s g] (.writeString g (str s))))

;; CONSTANTS - DEFs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Database connection:
;; using MongoOptions allows fine-tuning connection parameters,
;; like automatic reconnection (highly recommended for production environment)
;;   ==> http://clojuremongodb.info/articles/connecting.html
(def ^:private db-conn
    (let [^MongoOptions opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
          ^ServerAddress sa  (mg/server-address config/get-mongodb-host config/get-mongodb-port)
          conn               (mg/connect sa opts (mcred/create config/get-mongodb-username config/get-mongodb-database (.toCharArray config/get-mongodb-password)))
          db                 (mg/get-db conn config/get-mongodb-database)]
        db))

;; elements to be removed from database queries
(def ^:private remove-elems-list [:password :_id])

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function:
(defn- with-db
    [op & args]
    (apply op db-conn args))

;; Function:
(defn- get-all-by-field-withpsswd
    [coll-name map-values]
    (for [x (with-db mc/find-maps coll-name map-values)]
        (assoc x :_id (generate-string (get x :_id)))))

;; USER SCHEMA
;; Example: {:name "Pepe" :username "pepe" :password "password" :rol "user" :location "Spain"}
(defn- user-schema-instance1
    [body-doc operation id]
    (let [doc-map (parse-user-schema body-doc)]
        (cond
            (= operation "update") (assoc doc-map
                                       :password ((first (get-all-by-field-withpsswd "users" {:_id (ObjectId. id)})) :password))
            :else {:message "- operation not implemented -"})))

;; DOCUMENT SCHEMA
;; Example: {:name "test" :desc "test" :url "https://www....." :type "video" :tags "test" :stored "false"}
(defn- document-schema-instance1
    [body-doc operation]
    (let [doc-map (parse-document-schema body-doc)]
        (cond
            (= operation "update") doc-map
            :else {:message "- operation not implemented -"})))

;; MONGODB FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; QUERIES / TRANSACTIONS
;; Function: get all elements from collection / table
(defn get-all
    [coll-name]
    (case coll-name
        "users" (for [x (with-db mc/find-maps coll-name)]
                    (dissoc (assoc x :_id (generate-string (get x :_id))) :password))
        "documents" (with-db mc/find-maps coll-name {:type "video"})))

;; Function: get element from collection / table by id
(defn get-by-id
    [coll-name id]
    (case coll-name
        "users"     (if (>= (with-db mc/count coll-name {:_id (ObjectId. id)}) 1)
                        (gen-response CODE_OK
                                      (dissoc (assoc (with-db mc/find-one-as-map coll-name {:_id (ObjectId. id)}) :_id id) :password))
                        (gen-response CODE_ERROR {:message "warning: no users found"}))
        "documents" (if (>= (with-db mc/count coll-name {:_id (ObjectId. id)}) 1)
                        (gen-response CODE_OK
                                      (with-db mc/find-one-as-map coll-name {:_id (ObjectId. id)}))
                        (gen-response CODE_ERROR {:message "warning: no videos found"}))))

;; Function:
(defn get-doc-stored-by-id
    [id]
    (let [res (get-by-id "documents" id)]
        (if (= CODE_OK (res :code))
            (get-in res [:content :stored])
            "false")))

;; Function: Example (get-doc-publicid-by-id "56e957c2f06f6b31d884d175")
(defn get-doc-publicid-by-id
    [id]
    (let [res (get-by-id "documents" id)]
        (if (= CODE_OK (res :code))
            (get-in res [:content :name])
            "NotFound")))

;; Function: get elements by field(s)
(defn get-all-by-field
    [coll-name map-values]
    (case coll-name
        "users"     (gen-response CODE_OK
                                  (for [x (with-db mc/find-maps coll-name map-values)]
                                      (dissoc (assoc x :_id (generate-string (get x :_id))) :password)))
        "documents" (gen-response CODE_OK (with-db mc/find-maps coll-name map-values))))

;; Function: insert new element
(defn- insert
    [coll-name doc-map error-msg]
    (if-not (empty? (with-db mc/insert-and-return coll-name doc-map))
        (gen-response CODE_OK (get-all coll-name))
        (gen-response CODE_ERROR {:message (str "error: " error-msg)})))

;; Function:
(defn insert-new
    [coll-name body]
    (case coll-name
        "users"     (let [doc-map (user-schema-instance body "new" "")]
                        (if (empty? ((get-all-by-field coll-name {:username (get doc-map :username)}) :content))
                            (insert coll-name doc-map "user was not created")
                            (gen-response CODE_ERROR {:message "error: user already exists"})))
        "documents" (let [doc-map (document-schema-instance body "new")]
                        (if (empty? ((get-all-by-field coll-name {:name (get doc-map :name)}) :content))
                            (insert coll-name doc-map "video was not uploaded")
                            (gen-response CODE_ERROR {:message "error: (video) name already exists"})))))

;; Function: update element
(defn- update-elem
    [coll-name id data error-msg]
    (if (>= (with-db mc/count coll-name {:_id (ObjectId. id)}) 1)
        (do (with-db mc/update-by-id coll-name (ObjectId. id) data)
            (gen-response CODE_OK (get-all coll-name)))
        (gen-response CODE_ERROR (str "error: " error-msg))))

;; Function:
(defn update-by-id
    [coll-name id body]
    (case coll-name
        "users"     (update-elem "users" id (user-schema-instance1 body "update" id) "no users found")
        "documents" (update-elem "documents" id (document-schema-instance1 body "update") "no documents found")))

;; Function: delete element
(defn- delete
    [coll-name id error-msg]
    (if (>= (with-db mc/count coll-name {:_id (ObjectId. id)}) 1)
        (do (with-db mc/remove-by-id coll-name (ObjectId. id))
            (gen-response CODE_OK (get-all coll-name)))
        (gen-response CODE_ERROR {:message (str "error: " error-msg)})))

;; Function:
(defn delete-by-id
    [coll-name id]
    (case coll-name
        "users"     (delete coll-name id "no users found")
        "documents" (delete coll-name id "no documents found")))

;; Function: LOGIN ==> clojure.lang.PersistentArrayMap
(defn do-login
    "returns [clojure.lang.PersistentArrayMap], example: {:code '1', :content ({:rol 'admin', :username 'rsucasas', ...})}
    where :content is a clojure.lang.LazySeq"
    [username password ip]
    (let [res (get-all-by-field-withpsswd "users" {:username username})]
        (if-not (empty? res)
            (if (password/check password ((first res) :password))
                (gen-response CODE_OK (map #(apply dissoc (into {} %) remove-elems-list) res) "jwt" ip)
                (gen-response CODE_ERROR {:message "no users found - not valid username / password (1)"}))
            (gen-response CODE_ERROR {:message "no users found - not valid username / password (2)"}))))

;; INITIALIZE DATABASE
;; Function: delete all and add new data to new collections
(defn initialize
    []
    (log/debug "Initializing database: " config/get-mongodb-host ":" config/get-mongodb-port " " config/get-mongodb-database " ...")
    (mc/drop db-conn "users")
    (mc/drop db-conn "documents")
    (do
        (when-not (mc/exists? db-conn "users")
            (with-db mc/insert-and-return  "users" SUPER-ADMIN-USER)
            (with-db mc/insert-and-return  "users" DEFAULT-ADMIN-USER)
            (with-db mc/insert-and-return  "users" DEFAULT-ADMIN-USER2))
        (when-not (mc/exists? db-conn "documents")
            (with-db mc/insert-and-return  "documents" DEFAULT-DOCUMENT)))
    (gen-response CODE_OK {:message "database initialized"}))
