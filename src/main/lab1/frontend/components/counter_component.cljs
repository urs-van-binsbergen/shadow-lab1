(ns lab1.frontend.components.counter-component
  (:require [reagent.core :as r]))

(defonce global-counter (r/atom 0))

(defn counter-component []
  (let [counter (r/atom 0)]
    (fn []
      [:button
       {:type :button
        :style {:border "1px solid gray"}
        :on-click #(swap! counter inc)}
       @counter])))

(defn global-counter-component []
  (fn []
    [:button
     {:type :button
      :style {:border "1px solid gray"}
      :on-click #(swap! global-counter inc)}
     "global " @global-counter]))