(ns lab1.frontend.pages.home.home
  (:require ["/lab1/frontend/js/elefant.js" :as elefant :refer [elefant1
                                                                elefant2
                                                                getAll]]
            [lab1.frontend.components.counter :as counter]
            [lab1.frontend.components.timed-counter :as timed-counter]
            [lab1.frontend.route-names :as route-names]
            [reitit.frontend.easy :as rfe]))

(defn page []
  (js/console.log elefant/default)
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
    [:button {:type :button :on-click #(rfe/set-query (fn [q] {:x (rand)}) {:replace true})} "replaceState"]]

   [:h4 "JS integration tests"]
   [:div elefant/default " " elefant/elefant1 " " elefant1 " " elefant2]
   [:div "getAll: " (-> (getAll) str)]])