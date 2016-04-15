(ns softcare.db.db
    (:use [softcare.globals])
    (:require [softcare.config :as config]
              [softcare.db.mongodb :as mongodb]
              [softcare.db.h2db :as h2db]
              [softcare.db.mysql :as mysql]
              [softcare.logs.logs :as log]
              [softcare.admin.queries :as admq]
              [softcare.admin.users :as admu]
              [softcare.db.cloudinary :as cdn]))

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn- str-to-key
    [& txt]
    (keyword (apply str txt)))

;; LISTS FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn get-queries-list [] (admq/get-queries))

;;
(defn get-users-list [] (admu/get-users))

;; DATABASE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; login
(defn do-login
    [body]
    ;(future (admq/add-query-to-list (str-to-key "do-login-" (get body "ip"))))
    ;(future (admu/add-user-to-list (str-to-key (get body "username")) (get body "ip")))
    (let [username (get body "username")
          password (get body "password")
          ip       (get body "ip")]
        (cond
            (= config/get-db-type MONGODB) (mongodb/do-login username password ip)
            (= config/get-db-type H2DB) (h2db/do-login username password ip)
            (= config/get-db-type MYSQL) (mysql/do-login username password ip)
            :else {:message "- selected database not implemented -"})))

;; logout
(defn do-logout
    [token]
    (future (admq/add-query-to-list (str-to-key "do-logout")))
    {})

;; get all elements from collection / table
(defn get-all
    [coll]
    ;(future (admq/add-query-to-list (str-to-key "get-all-" coll)))
    (cond
        (= config/get-db-type MONGODB) (mongodb/get-all coll)
        (= config/get-db-type H2DB) (h2db/get-all coll)
        (= config/get-db-type MYSQL) (mysql/get-all coll)
        :else {:message "- selected database not implemented -"}))

;; get records / info from all documents: (get-records-info)
(defn get-records-info []
    ;(future (admq/add-query-to-list (str-to-key "get-records-info")))
    (cond
        (= config/get-db-type MONGODB) {:message "- selected database not implemented -"}
        (= config/get-db-type H2DB) {:message "- selected database not implemented -"}
        (= config/get-db-type MYSQL) (mysql/get-records-info)
        :else {:message "- selected database not implemented -"}))

;; get elements from collection / table by field value
(defn get-by-id
    [coll id]
    (future (admq/add-query-to-list (str-to-key "get-by-id-" coll "-" id)))
    (cond
        (= config/get-db-type MONGODB) (mongodb/get-by-id coll id)
        (= config/get-db-type H2DB) (h2db/get-by-id coll id)
        (= config/get-db-type MYSQL) (mysql/get-by-id coll id)
        :else {:message "- selected database not implemented -"}))

;;
(defn get-all-by-field
    [coll map-values]
    ;(future (admq/add-query-to-list (str-to-key "get-all-by-field-" coll)))
    (cond
        (= config/get-db-type MONGODB) (mongodb/get-all-by-field coll map-values)
        (= config/get-db-type H2DB) (h2db/get-all-by-field coll map-values)
        (= config/get-db-type MYSQL) (mysql/get-all-by-field coll map-values)
        :else {:message "- selected database not implemented -"}))

;;
(defn get-all-records
    [coll-name]
    (cond
        (= config/get-db-type MONGODB) {:message "- selected database not implemented -"}
        (= config/get-db-type H2DB) {:message "- selected database not implemented -"}
        (= config/get-db-type MYSQL) (mysql/get-all-records coll-name)
        :else {:message "- selected database not implemented -"}))

;; insert new element
(defn- insert
    [coll body]
    (cond
        (= config/get-db-type MONGODB) (mongodb/insert-new coll body)
        (= config/get-db-type H2DB) (h2db/insert-new coll body)
        (= config/get-db-type MYSQL) (mysql/insert-new coll body)
        :else {:message "- selected database not implemented -"}))

;;
(defn insert-new
    [coll body]
    ;(future (admq/add-query-to-list (str-to-key "insert-new-" coll)))
    (cond
        (= "documents" coll) (if (= (compare "true" (get body "stored")) 0)
                                 (let [res (cdn/store-file body)]
                                     ; 1st in cloudinary
                                     (if (= CODE_OK (res :code))
                                         ; in database
                                         (insert coll (assoc body "url" (res :url)))
                                         {:code CODE_ERROR :content "ERROR: not stored in cloudinary"}))
                                 ; only in database
                                 (insert coll body))
        (= "users" coll) (insert coll body)
        :else {:message "- not implemented -"}))

;; update element
(defn update-by-id
    [coll id body]
    ;(future (admq/add-query-to-list (str-to-key "update-by-id-" coll)))
    (cond
        (= config/get-db-type MONGODB) (mongodb/update-by-id coll id body)
        (= config/get-db-type H2DB) (h2db/update-by-id coll id body)
        (= config/get-db-type MYSQL) (mysql/update-by-id coll id body)
        :else {:message "- selected database not implemented -"}))

;; delete element
(defn delete-by-id
    [coll id]
    ;(future (admq/add-query-to-list (str-to-key "delete-by-id-" coll)))
    (cond
        (= config/get-db-type MONGODB) (mongodb/delete-by-id coll id)
        (= config/get-db-type H2DB) (h2db/delete-by-id coll id)
        (= config/get-db-type MYSQL) (mysql/delete-by-id coll id)
        :else {:message "- selected database not implemented -"}))

;; delete document (video ...) by id
(defn delete-doc-by-id
    [coll id]
    ;(future (admq/add-query-to-list (str-to-key "delete-by-id-" coll)))
    (cond
        (= config/get-db-type MONGODB) (do (when (= (mongodb/get-doc-stored-by-id id) "true")
                                               (cdn/delete-video (mongodb/get-doc-publicid-by-id id)))
                                           (mongodb/delete-by-id coll id))
        (= config/get-db-type H2DB) (h2db/delete-by-id coll id)
        (= config/get-db-type MYSQL) (mysql/delete-by-id coll id)
        :else {:message "- selected database not implemented -"}))

;;
(defn undefined-method [] {:message "- not implemented -"})

;; initialize database
(defn initialize []
    (cond
        (= config/get-db-type MONGODB) (mongodb/initialize)
        (= config/get-db-type H2DB) (h2db/initialize)
        (= config/get-db-type MYSQL) (mysql/initialize)
        :else {:message "- selected database not implemented -"}))

;; initialize logs
(defn initialize-logs []
    (do
        (admq/reset-queries)
        (admu/reset-users)))





;; (insert-new "documents"
;;             {"name" "music1",
;;              "desc" "....",
;;              "type" "music",
;;              "tags" "music",
;;              "stored" "true",
;;              "url" "C:/Users/Public/Music/Sample Music/Kalimba.mp3"})
