(ns softcare.utilities.jwtoken
    (:require [softcare.config :as config]
              [softcare.logs.logs :as log]
              [clj-jwt.core  :refer :all]
              [clj-jwt.key   :refer [private-key]]
              [clj-time.core :refer [now plus minutes]]
              [clj-time.coerce :as tc]))

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; validate token
(defn- validate-jwt
    [token]
    (try
        (-> token str->jwt (verify config/get-jwt-secret-token))
        (catch Exception e
            (do (log/error "error validating auth token")
                false))))

;; decode token
(defn- decode-jwt
    [token]
    (-> token str->jwt :claims))


;; PUBLIC FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; check token
(defn check-token
    [token]
    (if (validate-jwt token)
        (if (> (tc/to-long (now)) ((decode-jwt token) :exp-time))
            false
            true)
        false))

(defn check-token-admin-user
    [token]
    (if (check-token token)
        (if (= (compare "admin" ((decode-jwt token) :rol)) 0)
            false
            true)
        false))


;; create token
(defn create-token
    [m-user-data ip]
    (-> (assoc m-user-data
            :iat-time (tc/to-long (now))
            :exp-time (tc/to-long (plus (now) (minutes config/get-jwt-token-time)))
            :ip ip) jwt (sign :HS256 config/get-jwt-secret-token) to-str))

;; update token expiration time
(defn update-token
    [token]
    (-> (assoc (decode-jwt token) :exp-time (tc/to-long (plus (now) (minutes config/get-jwt-token-time))))
        jwt (sign :HS256 config/get-jwt-secret-token) to-str))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;(def DEFAULT-ADMIN-USER {:name "Roi Sucasas" :username "rsucasas" :password "password" :rol "admin" :location "Spain"})
;(create-token DEFAULT-ADMIN-USER "127.0.0.1")
