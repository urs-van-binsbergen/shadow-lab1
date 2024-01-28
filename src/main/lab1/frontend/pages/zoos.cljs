(ns lab1.frontend.pages.zoos
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [go <!]]
            [lab1.frontend.state :as state]
            [reitit.frontend.easy :as rfe]
            [lab1.frontend.route-names :as route-names]))


(defn zoos-cljs-http []
  (let [zoos (r/atom [])]
    (go (let [response (<! (http/get "http://localhost:3001/zoos"))]
          (reset! zoos (:body response))))
    (fn []
      [:div
       [:h4 "Zoos with cljs-http"]
       [:button {:on-click #(swap! zoos conj {:id 0 :name "Foo"})} "Add"]
       (for [{:keys [id name]} @zoos]
         [:div {:key id}
          [:a {:href (rfe/href ::route-names/zoo-detail {:id id})} id " " name]])])))

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

(defn detail-page []
  (let [zoo (r/atom nil)]
    (fn []
      (js/console.log "render zoo detail")
      (let [id (-> @state/route-match :path-params :id)]
        [:<>
         [:h3 "Zoo " id]
         [:a {:href "/zoos/2"} "-> zoo 2"]
         [:div "todo" (str @zoo)]]))))