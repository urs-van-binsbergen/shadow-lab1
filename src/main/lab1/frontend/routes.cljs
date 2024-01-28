(ns lab1.frontend.routes
  (:require [lab1.frontend.pages.examples :as examples]
            [lab1.frontend.pages.home :as home]
            [lab1.frontend.pages.zoos :as zoos]
            [lab1.frontend.route-names :as route-names]))

(def routes
  [["/"
    {:name ::route-names/home
     :view #'home/page}]
   ["/zoos"
    ["" {:name ::route-names/zoo-index
         :view #'zoos/index-page}]
    ["/:id" {:name ::route-names/zoo-detail
             :view #'zoos/detail-page}]]
   ["/examples" {:name ::route-names/examples
                 :view #'examples/index-page}]])

