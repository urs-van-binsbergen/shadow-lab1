(ns lab1.frontend.pages.home
  (:require [lab1.frontend.components.counter-component :refer [counter-component]]
            [lab1.frontend.route-names :as route-names]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))

(def seconds-elapsed (r/atom 0))

(defn timer-component []
  (fn []
    (js/setTimeout #(swap! seconds-elapsed inc) 1000)
    [:div
     "Seconds Elapsed: " @seconds-elapsed]))

(defn page []
  [:div
   [:h3 "home-page"]
   [timer-component]
   [:div "hello " [counter-component]]
   [:div "hello " [counter-component]]

   [:h4 "Some Routing tests"]
   [:ul
    [:li "on-click mit `navigate` " [:button {:type :button :on-click #(rfe/navigate ::route-names/zoo-index)} "Go to zoos"]]
    [:li "Link mit Pfad als href " [:a {:href "/zoos"} "Go to zoos"]]
    [:li "Link mit Route als href " [:a {:href (rfe/href ::route-names/zoo-index)} "Go to zoos"]]
    [:li "on-click mit `set-query` "
     [:button {:type :button :on-click #(rfe/set-query {:seconds @seconds-elapsed})} "add seconds with pushState"]
     [:button {:type :button :on-click #(rfe/set-query {:seconds @seconds-elapsed} {:replace true})} "add seconds with replaceState"]]]])