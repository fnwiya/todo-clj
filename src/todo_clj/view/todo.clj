(ns todo-clj.view.todo
  (:require [hiccup.form :as hf]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [todo-clj.view.layout :as layout]))

(defn error-messages [req]
  (when-let [errors (:errors req)]
    [:ul
     (for [[k v] errors
           msg v]
       [:li.error-message msg])]))

(defn todo-index-view [req todo-list]
  (->> [:section.card
        (when-let [{:keys [msg]} (:flash req)]
          [:dev.alert.alert-success [:strong msg]])
        [:h2 "todo list"]
        [:ul
         (for [{:keys [id title]} todo-list]
           [:li [:a {:href (str "/todo/" id)} title]])]]
       (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "add todo"]
        (hf/form-to
         [:post "/todo/new"]
         (anti-forgery-field)
         (error-messages req)
         [:input {:name :title :placeholder "input todo"}]
         [:button.bg-blue "add"])]
       (layout/common req)))

(defn todo-complete-view [req]
  (->> [:section.card
        [:h2 "complete adding todo"]]
       (layout/common req)))

(defn todo-show-view [req todo]
  (let [todo-id (:id todo)]
  (->> [:section.card
        (when-let [{:keys [msg]} (:flash req)]
          [:div.alert.alert-success [:strong msg]])
        [:h2 (:title todo)]
        [:a.wide-link {:href (str "/todo/" todo-id "/edit")} "edit"]
        [:a.wide-link {:href (str "/todo/" todo-id "/delete")} "delete"]]
       (layout/common req))))

(defn todo-edit-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "edit todo"]
          (hf/form-to
           [:post (str "/todo/" todo-id "/edit")]
         (anti-forgery-field)
           (error-messages req)
           [:input {:name :title :value (:title todo)
                    :placeholder "input todo"}]
           [:button.bg-blue "update"])]
         (layout/common req))))

(defn todo-delete-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "delete todo"]
          (hf/form-to
           [:post (str "/todo/" todo-id "/delete")]
         (anti-forgery-field)
           [:p "really?"]
           [:p "*" (:title todo)]
           [:button.bg-red "delete"])]
         (layout/common req))))
