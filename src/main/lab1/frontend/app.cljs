(ns lab1.frontend.app
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]
            [reitit.frontend.easy :as reitit-easy]
            [lab1.frontend.routing :as routing]
            [lab1.frontend.components.counter-component :refer [counter-component]]))

(defonce root (createRoot (js/document.getElementById "app")))

(defn nav []
  [:div
   [:span [:a {:href (reitit-easy/href ::routing/home)} "Home"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/login)} "Login"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/zoo-index)} "Zoos"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/examples)} "Examples"]]
   " | "
   [counter-component]])

(defn app-view []
  [:div
   [nav]
   [:hr]
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