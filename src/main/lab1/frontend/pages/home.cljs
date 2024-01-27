(ns lab1.frontend.pages.home
  (:require [lab1.frontend.components.counter-component :refer [counter-component]]
            [reagent.core :as r]))

(defn timer-component []
  (let [seconds-elapsed (r/atom 0)]
    (fn []
      (js/setTimeout #(swap! seconds-elapsed inc) 1000)
      [:div
       "Seconds Elapsed: " @seconds-elapsed])))

(defn page []
  [:div
   [:h3 "home-page"]
   [timer-component]
   [:div "hello " [counter-component]]
   [:div "hello " [counter-component]]
   [:h4 "Some Routing tests"]])