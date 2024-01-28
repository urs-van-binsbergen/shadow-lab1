(ns lab1.frontend.routing
  (:require [reagent.core :as r]
            [reitit.coercion.spec :as rcs]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rc]
            [reitit.frontend.easy :as re]
            [lab1.frontend.routes :as routes]))

(defonce match* (r/atom nil))

(defn start! []
  (re/start!
   ; router
   (rf/router routes/routes {:data {:coercion rcs/coercion}})
   ; on-navigate
   (fn [new-match]
     (swap! match* (fn [old-match]
                    (when new-match
                      (assoc new-match 
                             :controllers 
                             (rc/apply-controllers (:controllers old-match) new-match))))))
   ; opts
   {:use-fragment false}))
