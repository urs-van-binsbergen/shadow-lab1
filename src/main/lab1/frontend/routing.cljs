(ns lab1.frontend.routing
  (:require [reitit.frontend :as reitit-frontend]
            [reitit.frontend.easy :as reitit-easy]
            [reitit.coercion.spec :as reitit-coercion-spec]

            [reagent.core :as r]

            [lab1.frontend.pages.home :as home]
            [lab1.frontend.pages.login :as login]
            [lab1.frontend.pages.zoos :as zoos]
            [lab1.frontend.pages.examples :as examples]))

(def routes
  [["/" {:name ::home
         :view #'home/page}]
   ["/login" {:name ::login
              :view #'login/page}]
   ["/zoos" {:name ::zoo-index
              :view #'zoos/index-page}]
   ["/examples" {:name ::examples
             :view #'examples/index-page}]])

(defonce routes-state (r/atom nil))

(defn start! []
  (reitit-easy/start!
   ; router
   (reitit-frontend/router routes {:data {:coercion reitit-coercion-spec/coercion}})
   ; on-navigate
   (fn [matched-route] (reset! routes-state matched-route)) ; bei reitit-example: "match"
   ; opts
   {:use-fragment false}))