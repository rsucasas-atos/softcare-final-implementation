(ns softcare.config
    (:require [softcare.utilities.utils :refer [get-resource]]
              [lock-key.core :refer [decrypt decrypt-as-str decrypt-from-base64 encrypt encrypt-as-base64]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(def lock "supercalifragilisticoespialidoso")

;;
(defn- encrypt-word
    [dec-word]
    (encrypt-as-base64 dec-word lock))

;;
(defn- decrypt-word
    [enc-word]
    (decrypt-from-base64 enc-word lock))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; CONFIGURATION WITH MAPS
;; default configuration
(def default-config {:app {:name "multimedia-repository"
                           :version "default"
                           :jwt {:token ""
                                 :time 5}
                           :db "mongodb"}
                     :cldy {:cloud-name ""
                            :api-key ""
                            :api-secret ""}
                     :mongodb {:host "95.211.172.244"
                               :port 27017
                               ; database   "test2"   "tests"
                               :database "multimediarepo"
                               :username ""
                               :password ""}
                     :h2 {:classname "org.h2.Driver"
                          :subprotocol "h2:file"
                          :subname ""}})

;; read configuration values into map
(defn- read-configuration
    [path]
    (let [fpath (get-resource path)]
        (if (nil? fpath)
            default-config
            (read-string (slurp fpath)))))

;; properties (as map)
(def props (read-configuration "app.properties.clj"))

;; get properties vaules
(defn- conf [& path]                  ;; e.g. (conf :db :uri)
    (get-in props (vec path)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; GLOBAL CONFIGURATION VALUES
(def get-app-name (conf :app :name))

(def get-app-version (conf :app :version))

(def get-jwt-secret-token (decrypt-word (conf :app :jwt :token)))

;; database connection used by app
(def get-db-type (conf :app :db))

;; token expiration time
(def get-jwt-token-time (Integer. (conf :app :jwt :time)))

;; CLOUDINARY:
(def get-cldy-cloud-name (conf :cldy :cloud-name) )

(def get-cldy-api-key (decrypt-word(conf :cldy :api-key)))

(def get-cldy-api-secret (decrypt-word(conf :cldy :api-secret)))

;; MONGODB connection:
(def get-mongodb-host (conf :mongodb :host))

(def get-mongodb-port (Integer. (conf :mongodb :port)))

(def get-mongodb-database (conf :mongodb :database))

(def get-mongodb-username (decrypt-word (conf :mongodb :username)))

(def get-mongodb-password (decrypt-word (conf :mongodb :password)))

;; H2 connection:
(def get-h2-classname (conf :h2 :classname))

(def get-h2-subprotocol (conf :h2 :subprotocol))

(def get-h2-subname (conf :h2 :subname))

;; MYSQL connection:
(def get-mysql-classname (conf :mysql :classname))

(def get-mysql-subprotocol (conf :mysql :subprotocol))

(def get-mysql-connurl (conf :mysql :connurl))

(defn- get-mysql-connurl-from-env []
    (or (System/getenv get-mysql-connurl)
        (System/getProperty get-mysql-connurl)
        "jdbc:mysql://95.211.172.242:3306/mmrepo?user=superadmin&password=password"))

(defn- get-mysql-env []
    (if (or (System/getenv get-mysql-connurl)
            (System/getProperty get-mysql-connurl))
        true
        false))

;; MYSQL connection from ENVIRONMENT VARIABLES (if exist):
(def get-mysql-subname (if (get-mysql-env)
                           (first (re-find #"\//(\S+):(\d+)\/[a-zA-Z0-9]+" (get-mysql-connurl-from-env)))
                           (conf :mysql :subname)))

(def get-mysql-username (if (get-mysql-env)
                            (second (re-find #"\?user=([a-zA-Z0-9]+)" (get-mysql-connurl-from-env)))
                            (decrypt-word (conf :mysql :username))))

(def get-mysql-password (if (get-mysql-env)
                            (second (re-find #"\&password=([a-zA-Z0-9]+)" (get-mysql-connurl-from-env)))
                            (decrypt-word (conf :mysql :password))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;(encrypt-word "secret")
;(decrypt-word "4SgZWhxcxK537NJcYsi9sfUjwsWnKlLHwGqbiYr2eCM=")
;(Integer. (decrypt-word "aT5JWjscOFTwWn49Y4MOw2a6586wtnMGbQKK10X9aA4="))

; jdbc:mysql://<HOST>:<PORT>/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>


;; ;; FROM ENVIRONMENT VARIABLES:
;; (get-mysql-connurl-from-env)

;; (def subname (first (re-find #"\//(\S+):(\d+)\/[a-zA-Z0-9]+" l)))
;; (def user (nth (re-find #"(\?user=)([a-zA-Z0-9]+)" l) 2))
;; (def pswd (nth (re-find #"(\&password=)([a-zA-Z0-9]+)" l) 2))

;; {:classname "com.mysql.jdbc.Driver"
;;  :subprotocol "mysql"
;;  :subname "//95.211.172.242:3306/mmrepo"
;;  :username "Q27B0AgknyvD+LKjb8ri1MLWJ+CkznsKga32fZQEzCw="
;;  :password "4SgZWhxcxK537NJcYsi9sfUjwsWnKlLHwGqbiYr2eCM="
;;  :connurl "connurl"}

;; (second (re-find #"\&password=([a-zA-Z0-9]+)" l))
