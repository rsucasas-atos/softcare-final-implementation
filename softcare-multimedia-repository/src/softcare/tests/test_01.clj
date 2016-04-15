(def body {"name" "1111" "url" "2222" "tags" "3333"})

(defn- add-to-schema-data
    [data k body]
    (if (contains? body k)
        (assoc data (keyword k) (get body k))
        data))

(defn parse-document-schema2
    [b]
    (add-to-schema-data
        (add-to-schema-data
            (add-to-schema-data
                (add-to-schema-data
                    (add-to-schema-data
                        (add-to-schema-data {} "name" b) "desc" b) "url" b) "type" b) "tags" b) "stored" b))

(parse-document-schema2 body)
(rest body)
(first body)
(first (rest body))
(keyword (first (first body)))
(second (first body))

(contains? [:name :desc1 :url :type :tags :stored :created_at] :name)

(def mM {:name "test"
        :desc1 "test"
        :url "https://www.youtube.com/embed/vsjohNujiXU"
        :type "video"
        :tags "test"
        :stored "false"
        :created_at "asdasd"})

(contains? mM :names)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(let [m mM]
    (loop [r {}
           b body]
        (if (= (count b) 0)
            r
            (recur (if (contains? m (keyword (first (first b))))
                       (assoc r (keyword (first (first b))) (second (first b)))
                       r)
                   (rest b)))))

;; (loop [r {}
;;        b body]
;;     (if (= (count b) 0)
;;         r
;;         (recur (assoc r (keyword (first (first b))) (second (first b)))
;;                (rest b))))


;; (loop [r {}
;;        b body]
;;     (if (= (count b) 0)
;;         r
;;         (recur (assoc r (keyword (first (first b))) (second (first b)))
;;                (rest b))))


;; (loop [x 2
;;        b body]
;;     (if (= x 0)
;;         b
;;         (recur (- x 1) (rest b))))




;; (loop [x 2
;;        b body]
;;     (if (<= x 0)
;;         b
;;         (recur (if (= b 0)
;;                    (- x 1)
;;                    0)
;;                (rest b))))
