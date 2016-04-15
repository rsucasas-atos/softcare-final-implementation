(ns softcare.db.cloudinary
    (:use [softcare.globals])
    (:require [softcare.config :as config]
              ;[clojure.data.json :as json]
              )
    (:import [com.cloudinary Cloudinary]
             ;[com.cloudinary.utils ObjectUtils]
             [java.io File IOException]
             [java.util Map]))

;; CONSTANTS - DEFs ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; connection with CLOUDINARY
(def cloudinary-conn (new Cloudinary {"cloud_name" config/get-cldy-cloud-name
                                      "api_key" config/get-cldy-api-key
                                      "api_secret" config/get-cldy-api-secret}))

;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function:
(defn- get-cloudinary-type [t]
    (cond
        (or (= t "music") (= t "other")) "auto"
        (= t "book") "raw"
        (= t "video") "video"
        (= t "image") "image"
        :else "auto"))

;; PUBLIC FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; upload-file: upload file to CLOUDINARY ==> java.util.HashMap
(defn- upload-file
  "[File / String]...file-to-upload
   [PersistentArrayMap]...data-map, example: {'public_id' 'dss video' 'tags' 'modaclouds, tutorial'}"
  ([file-to-upload]
   (.upload (.uploader cloudinary-conn) file-to-upload nil))
  ([file-to-upload data-map]
   (.upload (.uploader cloudinary-conn) file-to-upload data-map)))


;; file-path public-id resource-type
(defn store-file
    [body]
    (try
        (let [res (upload-file (get body "url") {"public_id" (get body "name") "resource_type" (get-cloudinary-type (get body "type"))})]
            {:code CODE_OK :url (get res "url")})
        (catch Exception e
            {:code CODE_ERROR :message (str "caught exception: " (.getMessage e))})))


;; deletes video
(defn delete-video
    [public-id]
    (.deleteResources (.api cloudinary-conn) [public-id]
                      {"public_id" public-id
                       "resource_type" "video"}))


;; (get-cloudinary-type "other")
;; (upload-file
;;     "C:/Users/A572832/Google Drive/Civil_War_II_Manual[E-Book].pdf"
;;     {"public_id" "book3" "resource_type" (get-cloudinary-type "raw")})

; C:/Users/Public/Music/Sample Music/Kalimba.mp3
; C:/Users/A572832/Google Drive/Civil_War_II_Manual[E-Book].pdf
; C:/Users/Public/Pictures/Sample Pictures/Koala.jpg


;; (.deleteAllResources (.api cloudinary-conn) {"public_id" "Wildlife5_123"
;;                                              "resource_type" "video"})
