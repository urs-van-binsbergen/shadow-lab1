(ns lab1.frontend.pages.home
  (:require
   [lab1.frontend.components.counter-component :refer [counter-component]]))

(defn page []
  [:div
   [:h3 "home-page"]
   [:div "hello" [counter-component]]
   [:div "hello" [counter-component]]])