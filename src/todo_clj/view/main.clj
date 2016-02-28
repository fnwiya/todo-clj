(ns todo-clj.view.main
  (:require [todo-clj.view.layout :as layout]))

(defn home-view [req]
  (->> [:section.card
       [:h2 "home"]
       [:a {:href "/todo"} "todo list"]]
      (layout/common req)))
