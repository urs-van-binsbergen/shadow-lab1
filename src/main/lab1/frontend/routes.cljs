(ns lab1.frontend.routes
  (:require [lab1.frontend.pages.examples :as examples]
            [lab1.frontend.pages.home :as home]
            [lab1.frontend.pages.zoo-detail :as zoo-detail]
            [lab1.frontend.pages.zoo-index :as zoo-index]
            [lab1.frontend.route-names :as route-names]))

(def routes
  [["/"
    {:name ::route-names/home
     :view #'home/page}]
   ["/zoos"
    ["" {:name ::route-names/zoo-index
         :view #'zoo-index/page}]
    ["/:id"
     [""
      {:name ::route-names/zoo-detail
       :view #'zoo-detail/detail-page
       :params {:foo 11}}]
     ["/edit"
      {:name ::route-names/zoo-edit
       :view #'zoo-detail/edit-page}]]]
   ["/examples" {:name ::route-names/examples
                 :view #'examples/index-page}]])

