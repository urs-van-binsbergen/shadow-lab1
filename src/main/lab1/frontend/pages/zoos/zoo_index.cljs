(ns lab1.frontend.pages.zoos.zoo-index
  (:require [cljs.core.async :refer [<! go]]
            [lab1.frontend.pages.zoos.zoo-api :as zoo-api]
            [lab1.frontend.route-names :as route-names]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))

(def zoos (r/atom []))
(def error (r/atom nil))

(defn zoo-list []
  (go (let [response (<! (zoo-api/get-zoos))]
        (if (response :success)
          (do
            (reset! error nil)
            (reset! zoos (:body response)))
          (reset! error (str "Error " (:status response))))))
  (fn []
    [:div
     (when @error [:div [:b {:style {:color "red"}} @error]])
     [:a {:href (rfe/href ::route-names/zoo-create {:id "0"})} "Add"]
     (for [{:keys [id name]} @zoos]
       [:div {:key id}
        [:a {:href (rfe/href ::route-names/zoo-detail {:id id})} id " " name]])]))

(defn page []
  [:<>
   [:h3 "Zoos"]
   [zoo-list]])

(comment
  (defn zoos-with-js-fetch []
    (let [zoos-data (r/atom nil)]
      (-> (js/fetch "http://localhost:3302/zoos")
          (.then #(.json %))
          (.then #(reset! zoos-data (js->clj %))))
      (fn []
        [:div
         [:h4 "Zoos with js/fetch and Promise"]
         [:button {:on-click #(swap! zoos-data conj {"id" 0 "name" "Foo"})} "Add"]
         (when-let [zoos @zoos-data]
           (for [zoo zoos]
             [:div {:key (zoo "id")} (zoo "id") " " (zoo "name")]))]))))

