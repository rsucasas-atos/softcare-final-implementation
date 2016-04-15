(ns softcare.db.schemas
    (:require [softcare.utilities.utils :as utils]
              [crypto.password.bcrypt :as password]
              [softcare.utilities.jwtoken :as jwtoken]))

;; CONSTANTS - DEFs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def SUPER-ADMIN-USER {:name "softcare"
                       :username "superadmin"
                       :password (password/encrypt "password123")
                       :rol "admin"
                       :location "Spain"
                       :created_at (utils/date-now-formatted)})

(def DEFAULT-ADMIN-USER {:name "Roi Sucasas"
                         :username "rsucasas"
                         :password (password/encrypt "password")
                         :rol "admin"
                         :location "Spain"
                         :created_at (utils/date-now-formatted)})

(def DEFAULT-ADMIN-USER2 {:name "admin"
                          :username "admin"
                          :password (password/encrypt "admin123")
                          :rol "admin"
                          :location "Spain"
                          :created_at (utils/date-now-formatted)})

(def DEFAULT-DOCUMENT {:name "test"
                       :desc1 "test"
                       :url "https://www.youtube.com/embed/vsjohNujiXU"
                       :type "video"
                       :tags "test"
                       :stored "false"
                       :created_at (utils/date-now-formatted)})

;; SHARED FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function:
(defn create-x-schema-instance
    [m body]
    (loop [r {}
           b body]
        (if (= (count b) 0)
            r
            (recur (if (contains? m (keyword (first (first b))))
                       (assoc r (keyword (first (first b))) (second (first b)))
                       r)
                   (rest b)))))

;; USER SCHEMA
;; Function: Example: {:name "Pepe" :username "pepe" :password "password" :rol "user" :location "Spain"}
(defn parse-user-schema
    [body-doc]
    {:name (get body-doc "name")
     :username (get body-doc "username")
     :rol (get body-doc "rol")
     :location (get body-doc "location")
     :updated_at (utils/date-now-formatted)})

;; Function:
(defn user-schema-instance
    [body-doc operation id]
    (let [doc-map (parse-user-schema body-doc)]
        (cond
            (= operation "new") (assoc doc-map
                                    :created_at (utils/date-now-formatted)
                                    :password (password/encrypt (get body-doc "password")))
            (= operation "update") (assoc (create-x-schema-instance DEFAULT-ADMIN-USER body-doc)
                                       :updated_at (utils/date-now-formatted)) ;doc-map
            :else {:message "- operation not implemented -"})))

;; DOCUMENT SCHEMA
;; Function: Example: {:name "test" :desc "test" :url "https://www....." :type "video" :tags "test" :stored "false"}
(defn parse-document-schema
    [body-doc]
    {:name (get body-doc "name")
     :desc1 (get body-doc "desc")
     :url (get body-doc "url")
     :type (get body-doc "type")
     :tags (get body-doc "tags")
     :stored (get body-doc "stored")
     :updated_at (utils/date-now-formatted)})

;; Function:
(defn document-schema-instance
    [body-doc operation]
    (let [doc-map (parse-document-schema body-doc)]
        (cond
            (= operation "new") (assoc doc-map :created_at (utils/date-now-formatted))
            (= operation "update") doc-map
            :else {:message "- operation not implemented -"})))

;; RESPONSES ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function: create response
(defn gen-response
    ([res-code res-content]
     (assoc {:code res-code} :content res-content))
    ([res-code res-content command ip]
     (if (= command "jwt")
         (assoc {:code res-code}
             :content res-content
             :token (jwtoken/create-token (first res-content) ip))
         (assoc {:code res-code} :content res-content))))
