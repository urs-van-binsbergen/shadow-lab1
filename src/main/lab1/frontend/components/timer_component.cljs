(ns lab1.frontend.components.timer-component
  (:require [reagent.core :as r]))

(defonce counter (r/atom 0))

(defn timer-component []
  (r/with-let [timer-fn  (js/setInterval #(swap! counter inc) 1000)]
    [:span "timer count " @counter]
    (finally (js/clearInterval timer-fn))))