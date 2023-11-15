(ns lab1.frontend.pages.zoos
  (:require
   [reagent.core :as r]
   [cljs.core.async :refer [go]]
   [cljs.core.async.interop :refer-macros [<p!]]))

#_(go
    (-> (fn [resolve reject] (js/setTimeout #(resolve 42) 2000))
        js/Promise.
        <p!
        println))

(defn zoos-1 []
  (let [data (r/atom nil)]
    (-> (js/fetch "http://localhost:3001/zoos")
        (.then #(.json %))
        (.then #(reset! data (js->clj %))))
    (fn []
      [:div
       [:h4 "Zoos with js/fetch and Promise"]
       [:button {:on-click #(swap! data conj {"id" 0 "name" "Foo"})} "Add"]
       (when-let [zoos @data]
         (for [zoo zoos]
           [:div {:key (zoo "id")} (zoo "id") " " (zoo "name")]))])))

(defn index-page []
  [:<>
   [:h3 "Zoos"]
   [zoos-1]])