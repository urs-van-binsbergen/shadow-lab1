(ns lab1.frontend.pages.zoos
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            #_[cljs.core.async.interop :refer-macros [<p!]] ; promise-to-async
            ))


(defn zoos-cljs-http []
  (let [zoos (r/atom [])]
    (go (let [response (<! (http/get "http://localhost:3001/zoos"))]
          (reset! zoos (:body response))))
    (fn []
      [:div
       [:h4 "Zoos with cljs-http"]
       [:button {:on-click #(swap! zoos conj {:id 0 :name "Foo"})} "Add"]
       (for [zoo @zoos]
         [:div {:key (zoo :id)} (zoo :id) " " (zoo :name)])])))

(defn zoos-js-fetch []
  (let [zoos-data (r/atom nil)]
    (-> (js/fetch "http://localhost:3001/zoos")
        (.then #(.json %))
        (.then #(reset! zoos-data (js->clj %))))
    (fn []
      [:div
       [:h4 "Zoos with js/fetch and Promise"]
       [:button {:on-click #(swap! zoos-data conj {"id" 0 "name" "Foo"})} "Add"]
       (when-let [zoos @zoos-data]
         (for [zoo zoos]
           [:div {:key (zoo "id")} (zoo "id") " " (zoo "name")]))])))

(defn index-page []
  [:<>
   [:h3 "Zoos"]
   [zoos-js-fetch]
   [zoos-cljs-http]])