(ns softcare.logs.logs
    (:require [clojure.tools.logging :as logging]
              [softcare.config :as config]))

;; use to print logs on console
(def logs-on-off "on")

;; use to check not nil
(def not-nil? (complement nil?))

;;
(defn- print-log
    "prints log content on screen console"
    [debug-level txt]
    {:pre [(not-nil? debug-level) (not-nil? txt)]}
    (cond
        (= debug-level "DEBUG") (logging/debug txt)
        (= debug-level "INFO") (logging/info txt)
        (= debug-level "ERROR") (logging/error txt)
        (= debug-level "WARNING") (logging/warn txt)
        :else (logging/trace txt)))

;;
(defn pr-log
    [l-type & txt]
    (when (= logs-on-off "on")
        (print-log l-type (apply str "## Multimedia Repository " config/get-app-version " ## > " txt))))

;;
(defn debug [& txt] {:pre [(not-nil? txt)]}
    (apply pr-log "DEBUG" txt))

;;
(defn info [& txt] {:pre [(not-nil? txt)]}
    (apply pr-log "INFO" txt))


(defn error [& txt] {:pre [(not-nil? txt)]}
    (apply pr-log "ERROR" txt))


(defn warning [& txt] {:pre [(not-nil? txt)]}
    (apply pr-log "WARNING" txt))


(defn trace [& txt] {:pre [(not-nil? txt)]}
    (apply pr-log "TRACE" txt))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; TESTS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; (debug "trazando")
;; (info "trazandasdo" "...")
;; (error "trazandasdo" "...")
;; (warning "trazandasdo" "...")
