(ns lab1.frontend.pages.home.home
  (:require [lab1.frontend.components.counter :as counter]
            [lab1.frontend.components.timed-counter :as timed-counter]
            [lab1.frontend.route-names :as route-names]
            [reitit.frontend.easy :as rfe]))

(defn page []
  [:div
   [:h3 "home-page"]

   [:h4 "Some state and component tests"]
   [:div "counter " [counter/counter]]
   [:div "another counter " [counter/counter]]
   [:div "global counter " [counter/global-counter] [counter/global-counter]]
   [:div "counter with timer " [timed-counter/counter]]

   [:h4 "Some routing tests"]
   [:div "on-click mit `navigate` " [:button {:type :button :on-click #(rfe/navigate ::route-names/zoo-index)} "Go to zoos"]]
   [:div "Link mit Pfad als href " [:a {:href "/zoos"} "Go to zoos"]]
   [:div "Link mit Route als href " [:a {:href (rfe/href ::route-names/zoo-index)} "Go to zoos"]]
   [:div "on-click mit `set-query` "
    [:button {:type :button :on-click #(rfe/set-query (fn [q] {:x (rand)}))} "pushState"]
    [:button {:type :button :on-click #(rfe/set-query (fn [q] {:x (rand)}) {:replace true})} "replaceState"]]])