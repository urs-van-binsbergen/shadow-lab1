(ns lab1.frontend.components.counter-component
  (:require [reagent.core :as r]))

(defn counter-component []
  (let [counter (r/atom 0)]
    (fn []
      [:button
       {:type :button
        :style {:border "1px solid gray"}
        :on-click #(swap! counter inc)}
       @counter])))