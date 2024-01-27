(ns lab1.frontend.pages.zoos-preloaded
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [go <!]]))

(def zoos (r/atom []))

(defn index-page []
  [:<>
   [:h3 "Zoos preloaded"]
   [:div "Does not work :( The idea is that the view only changes after data is loaded"]
   (doall
    (for [i (range 2)]
      (for [zoo @zoos]
        [:div {:key (str i "-" (zoo :id))} (zoo :id) " " (zoo :name)])))])

(defn fetch []
  (go (let [response (<! (http/get "http://localhost:3001/zoos"))]
        (js/console.log "fetch response" response)
        (reset! zoos (:body response))
        (:body response) ; (this seems to bring us nowhere)
        )))