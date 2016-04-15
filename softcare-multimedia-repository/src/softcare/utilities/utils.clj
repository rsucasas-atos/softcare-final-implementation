(ns softcare.utilities.utils
    (:import java.text.SimpleDateFormat))

;; Functions ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn call [this & that]
    (cond
        (string? this) (apply (resolve (symbol this)) that)
        (fn? this)     (apply this that)
        :else          (conj that this)))


;; Files / Paths ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; get file or nil if error
(defn get-resource
    [path]
  (when path
    (-> (Thread/currentThread) .getContextClassLoader (.getResource path))))

;; get file path in a map
(defn get-resource-map
    [path]
    (when path
        (try
            {:res 1 :content (.getFile (clojure.java.io/resource path))}
        (catch Exception e
            {:res -1 :content (str "caught exception: " e)}))))


;; Parsers: string to number ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; parse string to number
;;     Example: (parse-number "1.000")
(defn parse-number
    "parse string 's' to integer"
    [s]
    (try
        (let [n (clojure.string/replace s #"\." "")]
            {:status "ok"
             :result (Integer. (re-find  #"\d+" n ))})
        (catch Exception e
            {:status "error"
             :result (str "Exception : value=" s " : " (.getMessage e))})))

;; parse string to number - integer
;;     Example: (parse-number-int "1.000")
(defn parse-number-int
    "parse string 's' to integer"
    [s]
    (try
        (let [n (clojure.string/replace s #"\." "")]
            (Integer. (re-find  #"\d+" n )))
        (catch Exception e
            -1)))

;; Date ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; get current time
(defn date-now
    []
    (new java.util.Date))

;; get formatted current time
(defn date-now-formatted
    ([]
     (.format (java.text.SimpleDateFormat. "yyyy/MM/dd HH:mm:ss") (date-now)))
    ([str-format]
     (.format (java.text.SimpleDateFormat. str-format) (date-now))))

