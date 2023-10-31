(ns lab1.frontend.app
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]
            [reitit.frontend.easy :as reitit-easy]
            [lab1.frontend.routing :as routing]))

(defonce root (createRoot (js/document.getElementById "app")))

(defn nav []
  [:ul
   [:li [:a {:href (reitit-easy/href ::routing/home)} "Home"]]
   [:li [:a {:href (reitit-easy/href ::routing/login)} "Login"]]])

(defn app-view []
  [:div
   [nav]
   [:h2 "render app-view"]
   (if-let [current-view (-> @routing/routes-state :data :view)]
     [current-view]
     [:div "unknown route"])])

(defn ^:dev/after-load render []
  (println "render root")
  (.render root (r/as-element [app-view])))

(defn init []
  (println "init")
  (routing/start!)
  (render))