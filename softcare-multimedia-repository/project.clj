;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Multimedia-Repository-Web (clojure version)
;; v0.1.3 .... 16.03.2016
;;
;;    json web token:  https://github.com/liquidz/clj-jwt
;;    ssl / https:     http://nginx.org/en/docs/http/configuring_https_servers.html#single_http_https_server
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defproject softcare-multimedia "0.1.4"
    :description "Multimedia-Repository-Web (clojure version)"
    :url "http://www.softcare.com"
    :min-lein-version "2.0.0"
    :dependencies [[org.clojure/clojure "1.8.0"]
                   [compojure "1.5.0"]                          ; ==> "1.4.0"  https://github.com/weavejester/compojure
                   [ring/ring-json "0.4.0"]                     ; ==> "0.4.0"  https://github.com/ring-clojure/ring-json
                   [ring/ring-defaults "0.2.0"]                 ; ==> "0.1.5"  https://github.com/ring-clojure/ring-defaults
                   [org.clojure/tools.logging "0.3.1"]          ; ==> "0.3.1"  https://github.com/clojure/tools.logging
                   [log4j/log4j "1.2.17"                        ; ==> "1.2.17" http://logging.apache.org/log4j/1.2/  **JAVA**
                    :exclusions [javax.mail/mail
                                 javax.jms/jms
                                 com.sun.jdmk/jmxtools
                                 com.sun.jmx/jmxri]]
                   [com.novemberain/monger "3.0.1"]             ; ==> "3.0.1" https://github.com/michaelklishin/monger
                   [com.cloudinary/cloudinary-http44 "1.3.0"]   ; ==> "1.2.2" https://github.com/cloudinary/cloudinary_java  **JAVA**
                   [clj-jwt "0.1.1"]                            ; ==> "0.1.1" https://github.com/liquidz/clj-jwt
                   [crypto-password "0.2.0"]                    ; ==> "0.1.3" https://github.com/weavejester/crypto-password
                   [lock-key "1.4.1"]                           ; ==> "1.4.1" https://github.com/clavoie/lock-key
                   [org.clojure/java.jdbc "0.4.2"]              ; ==> "0.4.2" https://github.com/clojure/java.jdbc
                   [com.h2database/h2 "1.4.191"]
                   [com.mchange/c3p0 "0.9.5.2"]                 ;[c3p0/c3p0 "0.9.1.2"] ==> https://github.com/swaldman/c3p0
                   [mysql/mysql-connector-java "5.1.38"]        ; ==> "5.1.38" http://dev.mysql.com/doc/connector-j/en/
                   [org.clojure/math.numeric-tower "0.0.4"]
                   [net.bull.javamelody/javamelody-core "1.59.0"]
                   [com.thoughtworks.xstream/xstream "1.4.9"]
                   [org.jrobin/jrobin "1.5.9"]
                   [com.uswitch/clj-soap "0.2.3"]]
    :plugins [[lein-ring "0.9.7"]]                              ; ==> "0.9.7" https://github.com/weavejester/lein-ring
    :ring {:handler softcare.handler/app
           :port 8081
           :open-browser? false
           :resources-war-path "WEB-INF/classes/"}
    :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                    [ring/ring-mock "0.3.0"]]}}
    ;; jvm configuration
    :jvm-opts ["-Xmx512M"])
;;
;; :dev :dependencies
;;     * ring/ring-mock "0.3.0" ==> https://github.com/weavejester/ring-mock
;;         ==> Library to create mock ring requests for unit tests http://weavejester.github.com/ring-mock
;;     * javax.servlet/servlet-api "2.5" ==> https://github.com/ring-clojure/ring
;;         ==> From version 1.2.1 onward, the ring/ring-core package no longer comes with the javax.servlet/servlet-api
;;             package as a dependency
