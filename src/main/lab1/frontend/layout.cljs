(ns lab1.frontend.layout
  (:require [lab1.frontend.components.counter-component :refer [global-counter-component]]
            [lab1.frontend.components.timer-component :refer [timer-component]]
            [lab1.frontend.route-names :as route-names]
            [lab1.frontend.routing :as routing]
            [reitit.frontend.easy :as rfe]))

(defn nav []
  [:div
   [:span [:a {:href (rfe/href ::route-names/home)} "Home"]]
   " | "
   [:span [:a {:href (rfe/href ::route-names/login)} "Login"]]
   " | "
   [:span [:a {:href (rfe/href ::route-names/zoo-index)} "Zoos"]]
   " | "
   [:span [:a {:href (rfe/href ::route-names/zoo-preloaded-index)} "Zoos preloaded"]]
   " | "
   [:span [:a {:href (rfe/href ::route-names/examples)} "Examples"]]])

(defn app-view []
  [:div
   [nav]
   [timer-component]
   " | "
   [global-counter-component]
   [:hr]
   (if-let [current-view (-> @routing/match* :data :view)]
     [current-view]
     [:div "unknown route"])])
