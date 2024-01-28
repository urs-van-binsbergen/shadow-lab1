(ns lab1.frontend.components.timed-counter
  (:require [reagent.core :as r]))

(defonce ^:private state (r/atom 0))

(defn counter []
  (r/with-let [timer-fn  (js/setInterval #(swap! state inc) 1000)]
    [:span "timer count " @state]
    (finally (js/clearInterval timer-fn))))