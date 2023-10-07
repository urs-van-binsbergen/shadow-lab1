(ns lab1.frontend.app
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]))

(defonce root (createRoot (js/document.getElementById "app")))

(defn- main []
  [:ul
   [:li "Hello"]
   [:li {:style {:color "green"}} "World!"]])

(defn ^:dev/after-load start []
  (println "start")
  (.render root (r/as-element [main])))

(defn init []
  (println "init")
  (start))