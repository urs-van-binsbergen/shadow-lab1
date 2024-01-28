(ns lab1.frontend.app
  (:require ["react-dom/client" :refer [createRoot]]
            [lab1.frontend.components.counter-component :refer [counter-component]]
            [lab1.frontend.route-names :as route-names]
            [lab1.frontend.routing :as routing]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))

(defonce root (createRoot (js/document.getElementById "app")))

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
   [:span [:a {:href (rfe/href ::route-names/examples)} "Examples"]]
   " | "
   [counter-component]])

(defn app-view []
  [:div
   [nav]
   [:hr]
   (if-let [current-view (-> @routing/match* :data :view)]
     [current-view]
     [:div "unknown route"])])

(defn render []
  (js/console.log "render root")
  (.render root (r/as-element [app-view])))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn init []
  (js/console.log "init")
  (routing/start!)
  (render))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn ^:dev/after-load after-load []
  (render))