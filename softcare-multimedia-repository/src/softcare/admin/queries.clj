(ns softcare.admin.queries
    (:require [softcare.logs.logs :as log]))


;; LISTs
(def queries-list (atom {}))

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn- add-new-query
    [query]
    (swap! queries-list assoc query {:txt "" :count 1}))

;;
(defn- inc-query-value
    [query]
    (swap! queries-list update-in [query :count] inc))

;; PUBLIC FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn add-query-to-list
    [query]
    (if (nil? ((deref queries-list) query))
        (add-new-query query)
        (inc-query-value query)))

;;
(defn get-queries [] (deref queries-list))

;;
(defn reset-queries [] (reset! queries-list {}))
