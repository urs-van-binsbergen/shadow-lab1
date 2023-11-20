(ns lab1.frontend.routing
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reitit.frontend :as reitit-frontend]
            [reitit.frontend.easy :as reitit-easy]
            [reitit.coercion.spec :as reitit-coercion-spec]
            [reitit.frontend.controllers :as reitit-controllers]

            [reagent.core :as r]

            [lab1.frontend.pages.home :as home]
            [lab1.frontend.pages.login :as login]
            [lab1.frontend.pages.zoos :as zoos]
            [lab1.frontend.pages.zoos-preloaded :as zoos-preloaded]
            [lab1.frontend.pages.examples :as examples]))

(def routes
  [["/" {:name ::home
         :view #'home/page}]
   ["/login" {:name ::login
              :view #'login/page}]
   ["/zoos" {:name ::zoo-index
             :view #'zoos/index-page
             :controllers [{:start #(js/console.log "1 start")
                            :stop #(js/console.log "1 stop")}]}]
   ["/zoos-preloaded" {:name ::zoo-preloaded-index
                       :view #'zoos-preloaded/index-page
                       :controllers [{:start zoos-preloaded/fetch
                                      :stop #(js/console.log "2 stop")}]}]
   ["/examples" {:name ::examples
                 :view #'examples/index-page}]])

(defonce match (r/atom nil))

(defn start! []
  (reitit-easy/start!
   ; router
   (reitit-frontend/router routes {:data {:coercion reitit-coercion-spec/coercion}})
   ; on-navigate
   (fn [new-match]
     (swap! match (fn [old-match]
                    (js/console.log "old-match" (:path old-match) "new-match" (:path new-match))
                    (if new-match
                      (assoc new-match :controllers (reitit-controllers/apply-controllers (:controllers old-match) new-match))))))
   ; opts
   {:use-fragment false}))