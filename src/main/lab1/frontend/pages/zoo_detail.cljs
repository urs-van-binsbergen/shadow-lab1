(ns lab1.frontend.pages.zoo-detail 
  (:require [cljs-http.client :as http]
            [clojure.core.async :refer [<! go]]
            [lab1.frontend.state :as state]
            [reagent.core :as r]))

(defn zoo-detail [id]
  (js/console.log "init" id)
  (let [zoo (r/atom nil)]
    (go (let [response (<! (http/get (str "http://localhost:3001/zoos/" id)))]
          (reset! zoo (:body response))))
    (fn [id]
      (js/console.log "render" id)
      [:<>
       [:h3 "Zoo " id]
       [:a {:href "/zoos/1"} "[1]"] " "
       [:a {:href "/zoos/2"} "[2]"]
       (if-let [{:keys [id name]} @zoo]
         [:div "ID " id " / Name " name]
         [:div "..."])])))

(defn page []
  (let [id (-> @state/route-match :path-params :id)]
    (js/console.log "render zoo" id)
    ^{:key id} [zoo-detail id]))