(ns lab1.frontend.routing
  (:require [reitit.frontend :as reitit-frontend]
            [reitit.frontend.easy :as reitit-easy]
            [reitit.coercion.spec :as reitit-coercion-spec]

            [reagent.core :as r]

            [lab1.frontend.pages.home :as home]
            [lab1.frontend.pages.login :as login]))

(def routes
  [["/" {:name ::home
         :view #'home/page}]
   ["/login" {:name ::login
              :view #'login/page}]])

(defonce routes-state (r/atom nil))

(defn start! []
  (reitit-easy/start!
   ; router
   (reitit-frontend/router routes {:data {:coercion reitit-coercion-spec/coercion}})
   ; on-navigate
   (fn [matched-route] (reset! routes-state matched-route)) ; bei reitit-example: "match"
   ; opts
   {:use-fragment false}))