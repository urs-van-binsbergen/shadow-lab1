(ns lab1.frontend.routing
  (:require [lab1.frontend.routes :as routes]
            [lab1.frontend.state :as state]
            [reitit.coercion.spec :as rcs]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rc]
            [reitit.frontend.easy :as re]))

(defn on-navigate [new-match]
  (swap! state/route-match
         (fn [old-match]
           (when new-match
             (assoc new-match
                    :controllers
                    (rc/apply-controllers (:controllers old-match) new-match))))))

(defn start! []
  (re/start!
   ; router
   (rf/router routes/routes {:data {:coercion rcs/coercion}})
   ; on-navigate
   on-navigate
   ; opts
   {:use-fragment false}))
