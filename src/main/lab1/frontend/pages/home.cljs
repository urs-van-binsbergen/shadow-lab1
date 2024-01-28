(ns lab1.frontend.pages.home
  (:require [lab1.frontend.components.counter-component :refer [counter-component global-counter-component]]
            [lab1.frontend.route-names :as route-names]
            [reitit.frontend.easy :as rfe]))

(defn page []
  [:div
   [:h3 "home-page"]
   [:div "counter " [counter-component]]
   [:div "another counter " [counter-component]]
   [:div "global counter " [global-counter-component]]

   [:h4 "Some Routing tests"]
   [:ul
    [:li "on-click mit `navigate` " [:button {:type :button :on-click #(rfe/navigate ::route-names/zoo-index)} "Go to zoos"]]
    [:li "Link mit Pfad als href " [:a {:href "/zoos"} "Go to zoos"]]
    [:li "Link mit Route als href " [:a {:href (rfe/href ::route-names/zoo-index)} "Go to zoos"]]
    [:li "on-click mit `set-query` "
     [:button {:type :button :on-click #(rfe/set-query (fn [q] {:x (rand)}))} "pushState"]
     [:button {:type :button :on-click #(rfe/set-query (fn [q] {:x (rand)}) {:replace true})} "replaceState"]]]])