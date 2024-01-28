(ns lab1.frontend.layout
  (:require [lab1.frontend.route-names :as route-names]
            [lab1.frontend.state :as state]
            [reitit.frontend.easy :as rfe]))

(defn nav []
  [:div
   [:span [:a {:href (rfe/href ::route-names/home)} "Home"]]
   " | "
   [:span [:a {:href (rfe/href ::route-names/zoo-index)} "Zoos"]]
   " | "
   [:span [:a {:href (rfe/href ::route-names/examples)} "Examples"]]])

(defn app-view []
  [:div
   [nav]
   [:hr]
   (if-let [current-view (-> @state/route-match :data :view)]
     [current-view]
     [:div "unknown route"])])
