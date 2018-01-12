(ns serveasy-rest.handler
    (:require [compojure.api.sweet :refer :all]
      [ring.util.http-response :refer :all]
      [schema.core :as s]
      [taoensso.carmine :as car :refer (wcar)]))

(def server-conn {:pool {} :spec {:uri "redis://127.0.0.1:6379/"}})
(defmacro wcar* [& body] `(car/wcar server-conn ~@body))

(defn reduce-results [data]
      (persistent!
        (reduce
          (fn [acc0 item-vector]
              (reduce
                (fn [acc1 item]
                    (conj! acc1 item))
                acc0 item-vector))
          (transient [])
          data)))

(def app
  (api
    {:swagger
     {:ui   "/"
      :spec "/swagger.json"
      :data {:info {:title       "Serveasy REST api"
                    :description "by Clojure"}
             :tags [{:name        "api",
                     :description "some apis"}]}}}

    (GET "/logs" []
         :query-params [user_id :- String
                        start :- Long
                        end :- Long]
         (let [result (wcar* (car/zrangebyscore (str "logs:" user_id) start end))
               out (reduce-results result)]
              (ok out)))

    (PUT "/logs" []
         :body-params [user_id :- String
                       timestamp :- Long
                       logs]
         (wcar* (car/zadd (str "logs:" user_id) timestamp logs))
         (ok {:success true}))))
