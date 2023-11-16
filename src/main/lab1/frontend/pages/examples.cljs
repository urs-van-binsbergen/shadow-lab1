(ns lab1.frontend.pages.examples
  (:require
   [reagent.core :as r]))

(def state (r/atom {:foo {:bar "BAR"}
                    :baz "BAZ"
                    :quux "QUUX"
                    :number 10}))

; -- mutations

(defn update-baz []
  (swap! state update :baz concat "!"))

(defn update-bar []
  (swap! state update-in [:foo :bar] concat "!"))

(defn plus-1 []
  (swap! state update :number + 1))

(defn plus-2 []
  (swap! state update :number + 2))

; -- subscriptions

(def bar-cursor (r/cursor state [:foo :bar]))

(def number-reaction (r/reaction (-> @state :number)))

(def even-odd-reaction (r/reaction (-> @state :number (#(if (even? %) "even" "odd")))))

(defn number-state []
  (js/console.log "number-state executes")
  (:number @state))

(defn number-track []
  (js/console.log "number-track executes")
  (if (even? @(r/track number-state)) "even" "odd"))

; -- components

(defn quux-component []
  (js/console.log "quux-component is rendering (although quux did not change)")
  [:div "quux-component [:quux] = " (:quux @state)])

(defn bar-component []
  (js/console.log "bar-component is rendering")
  [:div "bar-component [:foo :bar] = " @bar-cursor])

(defn number-component []
  (js/console.log "number-component is rendering")
  [:div "number-component [:number] = " @number-reaction])

(defn even-odd-component []
  (js/console.log "even-odd-component is rendering")
  [:div "even-odd-component even? :number = " @even-odd-reaction])

(defn number-track-component []
  (js/console.log "number-track-component is rendering")
  [:div "number-track-component " @(r/track number-track)])

(defn index-page []
  (js/console.log "index-page is rendering")
  [:<>
   [quux-component]
   [bar-component]
   [number-component]
   [even-odd-component]
   [number-track-component]
   [:button {:on-click update-baz} "update baz"]
   [:button {:on-click update-bar} "update bar"]
   [:button {:on-click plus-1} "+ 1"]
   [:button {:on-click plus-2} "+ 2"]])