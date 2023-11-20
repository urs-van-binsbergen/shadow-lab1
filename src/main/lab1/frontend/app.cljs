(ns lab1.frontend.app
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]
            [reitit.frontend.easy :as reitit-easy]
            [lab1.frontend.routing :as routing]
            [lab1.frontend.components.counter-component :refer [counter-component]]
            [fipp.edn :as fedn]))

(defonce root (createRoot (js/document.getElementById "app")))

(defn nav []
  [:div
   [:span [:a {:href (reitit-easy/href ::routing/home)} "Home"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/login)} "Login"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/zoo-index)} "Zoos"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/zoo-preloaded-index)} "Zoos preloaded"]]
   " | "
   [:span [:a {:href (reitit-easy/href ::routing/examples)} "Examples"]]
   " | "
   [counter-component]])

(defn app-view []
  #_(js/console.log @routing/match)
  [:div
   [nav]
   [:hr]
   (if-let [current-view (-> @routing/match :data :view)]
     [current-view]
     [:div "unknown route"])
   [:pre (with-out-str (fedn/pprint @routing/match))]])

(defn ^:dev/after-load render []
  (println "render root")
  (.render root (r/as-element [app-view])))

(defn init []
  (println "init")
  (routing/start!)
  (render))