(ns lab1.frontend.routing
  (:require [lab1.frontend.pages.examples :as examples]
            [lab1.frontend.pages.home :as home]
            [lab1.frontend.pages.login :as login]
            [lab1.frontend.pages.zoos :as zoos]
            [lab1.frontend.pages.zoos-preloaded :as zoos-preloaded]
            [reagent.core :as r]
            [reitit.coercion.spec :as rcs]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rc]
            [reitit.frontend.easy :as re]))

(def routes
  [["/"
    {:name ::home
     :view #'home/page}]
   ["/login"
    {:name ::login
     :view #'login/page}]
   ["/zoos"
    ["" {:name ::zoo-index
         :view #'zoos/index-page}]
    ["/:id" {:name ::zoo-detail
             :view #'zoos/detail-page}]]
   ["/zoos-preloaded" {:name ::zoo-preloaded-index
                       :view #'zoos-preloaded/index-page}]
   ["/examples" {:name ::examples
                 :view #'examples/index-page}]])

(defonce match* (r/atom nil))

(defn start! []
  (re/start!
   ; router
   (rf/router routes {:data {:coercion rcs/coercion}})
   ; on-navigate
   (fn [new-match]
     (swap! match* (fn [old-match]
                    (when new-match
                      (assoc new-match 
                             :controllers 
                             (rc/apply-controllers (:controllers old-match) new-match))))))
   ; opts
   {:use-fragment false}))