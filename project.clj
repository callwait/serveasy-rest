(defproject serveasy-rest "0.1.0"
  :description "simple rest api"
  :url "will_be_later"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.0"]
                 [ring/ring-defaults "0.2.1"]
                 [metosin/compojure-api "1.1.11"]
                 [com.taoensso/carmine "2.17.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:port    80
         :handler serveasy-rest.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
