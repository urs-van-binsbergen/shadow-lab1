(ns lab1.frontend.pages.zoos.zoo-index
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [go <!]]
            [reitit.frontend.easy :as rfe]
            [lab1.frontend.route-names :as route-names]))


(defn zoo-list []
  (let [zoos (r/atom [])]
    (go (let [response (<! (http/get "http://localhost:3001/zoos"))]
          (reset! zoos (:body response))))
    (fn []
      [:div
       [:h4 "Zoos with cljs-http"]
       [:a {:href (rfe/href ::route-names/zoo-create {:id "0"})} "Add"]
       (for [{:keys [id name]} @zoos]
         [:div {:key id}
          [:a {:href (rfe/href ::route-names/zoo-detail {:id id})} id " " name]])])))

(defn page []
  (js/console.log "render zoo index")
  [:<>
   [:h3 "Zoos"]
   [zoo-list]])

(comment
  (defn zoos-with-js-fetch []
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
             [:div {:key (zoo "id")} (zoo "id") " " (zoo "name")]))]))))

