{:app {:name "multimedia-repository"
       :version "0.1.4"
       :jwt {:token "<ENCRYPTED TOKEN>"
             :time 5}
       :db "<SELECTED DATABASE>"}
 :cldy {:cloud-name "<CLOUD NAME>"
        :api-key "<API KEY>"
        :api-secret "<API SECRET>"}
 :mongodb {:host "<HOST IP>"
           :port 27017
           :database "multimediarepo"
           :username "<ENCRYPTED USERNAME>"
           :password "<ENCRYPTED PASSWORD>"}
 :h2 {:classname "org.h2.Driver"
      :subprotocol "h2:file"
      :subname "db"
      :username "<ENCRYPTED USERNAME>"
      :password "<ENCRYPTED PASSWORD>"}
 :mysql {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname "<SUBNAME>"
         :username "<ENCRYPTED USERNAME>"
         :password "<ENCRYPTED PASSWORD>"
         :connurl "connurl"}}

