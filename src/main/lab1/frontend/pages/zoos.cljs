(ns lab1.frontend.pages.zoos
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [go <!]]))


(defn zoos-cljs-http []
  (let [zoos (r/atom [])]
    (go (let [response (<! (http/get "http://localhost:3001/zoos"))]
          (reset! zoos (:body response))))
    (fn []
      [:div
       [:h4 "Zoos with cljs-http"]
       [:button {:on-click #(swap! zoos conj {:id 0 :name "Foo"})} "Add"]
       (for [zoo @zoos]
         [:div {:key (zoo :id)}
          [:a {:href "/zoos/1"} (zoo :id) " " (zoo :name)]])])))

#_(defn zoos-js-fetch []
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
  (js/console.log "render zoo index")
  [:<>
   [:h3 "Zoos"]
   [zoos-cljs-http]])

(defn detail-page [id]
  (js/console.log "render zoo detail")
  [:<>
   [:h3 "Zoo"]
   [:div "todo" id]])