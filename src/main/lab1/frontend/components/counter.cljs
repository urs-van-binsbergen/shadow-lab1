(ns lab1.frontend.components.counter
  (:require [reagent.core :as r]))

(defonce ^:private state (r/atom 0))

(defn counter []
  (let [state (r/atom 0)]
    (fn []
      [:button
       {:type :button
        :style {:border "1px solid gray"}
        :on-click #(swap! state inc)}
       @state])))

(defn global-counter []
  (fn []
    [:button
     {:type :button
      :style {:border "1px solid gray"}
      :on-click #(swap! state inc)}
     "global " @state]))