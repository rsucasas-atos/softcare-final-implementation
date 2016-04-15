;; HANDLER - REST API
(ns softcare.handler
    (:use [softcare.globals]
          [compojure.core]
          [ring.util.response])
    (:require [compojure.handler :as handler]
              [compojure.route :as route]
              [ring.middleware.json :as middleware]
              [softcare.db.db :as db]
              [softcare.config :as config]
              [softcare.logs.logs :as log]
              [softcare.utilities.jwtoken :as jwtoken]))


;; HEADER FIELD NAME used for authentication
(def AUTH "authorization")
(def IP "ip")


;; PRIVATE FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
(defn- add-token-to-function-res
    [headers function]
    (if (instance? clojure.lang.PersistentArrayMap function)
        (if (= CODE_OK (function :code))
            (assoc function :token (jwtoken/update-token (headers AUTH)))
            function)
        function))

;; response with token authentication
(defn- response-auth
    [headers function]
    (let [token (headers AUTH)]
        (response
            (if (jwtoken/check-token token)
                (add-token-to-function-res headers function)
                {:code CODE_WARN :content "invalid token"}))))

;; response with token authentication
(defn- response-auth-admin
    [headers function]
    (let [token (headers AUTH)]
        (response
            (if (jwtoken/check-token-admin-user token)
                (add-token-to-function-res headers function)
                {:code CODE_WARN :content "invalid token"}))))



;; ROUTES / APP / SERVER ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ROUTES
(defroutes app-routes
    ; index page - default
    (GET "/" [] (resource-response "index.html" {:root "public"}))

    ; API: all users
    (context "/api" []
             (defroutes api-users-routes
                 ; <<<< USERS >>>>
                 ; api/users >>>> get all users
                 (GET "/users"
                      {headers :headers}
                      (response (db/get-all "users"))) ; !!!!!!!!!!!!!!!!! no auth !!!!!!!!!!!!!!!!!!!!!!!!!!
                 ; api/users >>>> create new user
                 (POST "/users"
                       {headers :headers, body :body}
                       (response-auth headers (db/insert-new "users" body)))
                 ; api/users/:id >>>> get user by id
                 (GET "/users/:id"
                      {{id :id} :params, headers :headers}
                      (response-auth headers (db/get-by-id "users" id)))
                 ; api/users/:id >>>> update user by id
                 (PUT "/users/:id"
                      {{id :id} :params, headers :headers, body :body}
                      (response-auth headers (db/update-by-id "users" id body)))
                 ; api/users/:id >>>> delete user by id
                 (DELETE "/users/:id"
                         {{id :id} :params, headers :headers}
                         (response-auth headers (db/delete-by-id "users" id)))
                 ; api/user/auth >>>> authentication
                 (POST "/user/auth"
                       {body :body}
                       (response (db/do-login body)))
                 ; api/user/validate >>>> validation
                 (GET "/user/validate"
                      {headers :headers}
                      (response-auth headers {:code "1" :content "valid user/token"}))
                 ; api/user/logout >>>> logout
                 (GET "/user/logout"
                      {headers :headers}
                      (response-auth headers (db/do-logout (headers AUTH))))

                 ; <<<< DOCUMENTS (VIDEOS, IMGs, BOOKS ...) >>>>
                 ; api/documents >>>> get all
                 (GET "/documents"
                      []
                      (response (db/get-all "documents"))) ; !!!!!!!!!!!!!!!!! DESKTOP APP - no auth !!!!!!!!
                 ; api/records/info >>>> get info about stored contet
                 (GET "/records/info"
                      []
                      (response (db/get-records-info))) ; !!!!!!!!!!!!!!!!! DESKTOP APP - no auth !!!!!!!!
                 ; api/documents >>>> create new
                 (POST "/documents"
                       {headers :headers, body :body}
                       (response-auth headers (db/insert-new "documents" body)))
                 ; api/documents/:id >>>> get by id
                 (GET "/documents/:id"
                      {{id :id} :params, headers :headers}
                      (response-auth headers (db/get-by-id "documents" id)))
                 ; api/documents/:id >>>> update by id
                 (PUT "/documents/:id"
                      {{id :id} :params, headers :headers, body :body}
                      (response-auth headers (db/update-by-id "documents" id body)))
                 ; api/documents/:id >>>> delete by id
                 (DELETE "/documents/:id"
                         {{id :id} :params, headers :headers}
                         (response-auth headers (db/delete-doc-by-id "documents" id)))
                 ; api/records/all >>>> get all records
                 (GET "/records/all"
                      {headers :headers}
                      (response-auth headers (db/get-all-records "documents")))
                 ; api/videos >>>> get all videos
                 (GET "/videos"
                      {headers :headers}
                      (response-auth headers (db/get-all-by-field "documents" {:type "video"})))
                 ; api/images >>>> get all images
                 (GET "/images"
                      {headers :headers}
                      (response-auth headers (db/get-all-by-field "documents" {:type "image"})))
                 ; api/music >>>> get all music
                 (GET "/music"
                      {headers :headers}
                      (response-auth headers (db/get-all-by-field "documents" {:type "music"})))
                 ; api/books >>>> get all books
                 (GET "/books"
                      {headers :headers}
                      (response-auth headers (db/get-all-by-field "documents" {:type "book"})))
                 ; api/others >>>> get others
                 (GET "/others"
                      {headers :headers}
                      (response-auth headers (db/get-all-by-field "documents" {:type "other"})))

                 ; >>>> api/cloud/documents
                 ; create new document in Cloudinary
                 (POST "/cloud/documents" {body :body} (response (db/undefined-method)))
                 ; >>>> api/cloud/document/:id
                 (DELETE "/cloud/document/:id" [id] (response (db/undefined-method)))))

    ; API: administrators
    (context "/admin" []
             (defroutes api-users-routes
                 ; initialize database
                 (GET "/db/initialize"
                      {headers :headers}
                      (response (db/initialize)))
                 ; get queries (info/logs)
                 (GET "/logs/queries"
                      {headers :headers}
                      (response (db/get-queries-list)))
                 (GET "/logs/users"
                      {headers :headers}
                      (response (db/get-users-list)))
                 ; initialize (info/logs)
                 (GET "/logs/initialize"
                      {headers :headers}
                      (response (db/initialize-logs)))))
                      ;(response-auth-admin headers (db/get-queries-list)))))

    ; routes
    (route/resources "/")
    (route/not-found (response {:message "-Not Found-"})))

;; APP - RUN SERVER
(def app
    (-> (handler/api app-routes)
        (middleware/wrap-json-response)
        (middleware/wrap-json-body)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; EXECUTE INIT
(do
    (log/info "Multimedia-Repository-Web " config/get-app-version " ...")
    ;(db/initialize)
    (db/initialize-logs))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;(use 'clojure.repl)
;(source PUT)
;;(doc response)
