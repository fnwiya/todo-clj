(ns todo-clj.view.main
  (:require [hiccup.core :as hc]))

(defn home-view [req]
  (-> (list
       [:h1 "home"]
       [:a {:href "/todo"} "todo list"])
      hc/html))
