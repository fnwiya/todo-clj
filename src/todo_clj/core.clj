(ns todo-clj.core
  (:require [ring.adapter.jetty :as server]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defonce server (atom nil))

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1>Hello, world!!</h1>"})

(defn start-server []
  (when-not @server
    (reset! server (server/run-jetty #'handler {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))
