(ns softcare.admin.users
    (:require [softcare.logs.logs :as log]
              [softcare.utilities.utils :as utils]))


;; LISTs
(def users-list (atom {}))

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn- add-new-user
    [username ip]
    (swap! users-list assoc username {:t-login (utils/date-now-formatted)
                                      :t-last-conn (utils/date-now-formatted)
                                      :ip ip}))

;;
(defn- update-user
    [username]
    (swap! users-list update-in [username :t-last-conn] (constantly (utils/date-now-formatted))))

;; PUBLIC FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn add-user-to-list
    [username ip]
    (if (nil? ((deref users-list) username))
        (add-new-user username ip)
        (update-user username)))

;;
(defn get-users [] (deref users-list))

;;
(defn reset-users [] (reset! users-list {}))
