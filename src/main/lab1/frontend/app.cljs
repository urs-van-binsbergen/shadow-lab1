(ns lab1.frontend.app
  (:require ["react-dom/client" :refer [createRoot]]
            [lab1.frontend.components.counter-component :refer [global-counter-component]]
            [lab1.frontend.components.timer-component :refer [timer-component]]
            [lab1.frontend.route-names :as route-names]
            [lab1.frontend.routing :as routing]
            [reagent.core :as r]
            [reagent.dom :as rdom]
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

(def REACT18 true)
(defonce root (when REACT18 (createRoot (js/document.getElementById "app"))))
(defn render []
  (js/console.log "render root")
  (if REACT18
    (.render root (r/as-element [app-view]))
    (rdom/render [app-view] (js/document.getElementById "app"))))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn init []
  (js/console.log "init")
  (routing/start!)
  (render))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn ^:dev/after-load after-load []
  (init))