(ns lab1.frontend.app
  (:require [reagent.core :as r]
            ["react-dom/client" :refer [createRoot]]
            [reitit.frontend :as reitit-frontend]
            [reitit.frontend.easy :as reitit-easy]
            [reitit.coercion.spec :as reitit-coercion-spec]
            [spec-tools.data-spec :as ds]))

(defonce root (createRoot (js/document.getElementById "app")))
(defonce routes-state (r/atom nil))

(defn nav []
  [:ul
   [:li [:a {:href (reitit-easy/href ::home)} "Home"]]
   [:li [:a {:href (reitit-easy/href ::login)} "Login"]]
   [:li [:a {:href "/unknown"} "Unknown Route"]]])

(defn home-page []
  (println "render home-page")
  [:div
   [:h3 "home-page"]])

(defn login-page []
  (println "render login-page")
  [:div
   [:h3 "login-page"]])

(defn app-view []
  [:div
   [nav]
   [:h2 "render app-view"]
   (if-let [current-view (-> @routes-state :data :view)]
     [current-view]
     [:div "unknown route"])])

(defn ^:dev/after-load render []
  (println "render root")
  (.render root (r/as-element [app-view])))

(def routes
  [["/" {:name ::home
         :view #'home-page}]
   ["/login" {:name ::login
              :view #'login-page}]])

(defn router-start! []
  (reitit-easy/start!
   ; router
   (reitit-frontend/router routes {:data {:coercion reitit-coercion-spec/coercion}})
   ; on-navigate
   (fn [matched-route] (reset! routes-state matched-route)) ; bei reitit-example: "match"
   ; opts
   {:use-fragment false}))

(defn init []
  (println "init")
  (router-start!)
  (render))