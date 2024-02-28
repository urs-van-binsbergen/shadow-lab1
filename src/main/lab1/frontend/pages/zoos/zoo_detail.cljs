(ns lab1.frontend.pages.zoos.zoo-detail
  (:require [clojure.core.async :refer [<! go]]
            [lab1.frontend.pages.zoos.zoo-api :as zoo-api]
            [lab1.frontend.route-names :as route-names]
            [lab1.frontend.state :as state]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))

(defn zoo-loader [id inner-component]
  (let [zoo (r/atom nil)
        error (r/atom nil)]
    (go (let [response (<! (zoo-api/get-zoo id))]
          (if (response :success)
            (do
              (reset! error nil)
              (reset! zoo (:body response)))
            (reset! error (str "Error " (:status response))))))
    (fn [id inner-component]
      (cond @error
            [:div [:b {:style {:color "red"}} @error]]
            @zoo
            [inner-component @zoo]
            :else
            [:div "Loading..."]))))

(def global-error (r/atom nil))

(defn fetch [f on-success]
  (reset! global-error nil)
  (go (let [response (<! (f))]
        (if (response :success)
          (on-success (response :body))
          (reset! global-error (str "Error " (:status response)))))))

(defn global-error-component []
  (when @global-error [:div [:b {:style {:color "red"}} @global-error]]))

(defn update-zoo [id form-values]
  (fetch #(zoo-api/put-zoo id form-values)
         #(rfe/navigate ::route-names/zoo-detail {:path-params {:id id}})))

(defn create-zoo [form-values]
  (fetch #(zoo-api/post-zoo form-values)
         #(rfe/navigate ::route-names/zoo-detail {:path-params {:id (-> % :id)}})))

(defn save-zoo [id form-values]
  (if id
    (update-zoo id form-values)
    (create-zoo form-values)))

(defn delete-zoo [id]
  (fetch #(zoo-api/delete-zoo id)
         #(rfe/navigate ::route-names/zoo-index)))

(defn zoo-show [{:keys [id name]}]
  [:<>
   [:div "ID " id " / Name " name]
   [:div
    [:button {:type :button :on-click #(rfe/navigate ::route-names/zoo-edit {:path-params {:id id}})} "Edit"]
    [:button {:type :button :on-click #(when (js/confirm "really?") (delete-zoo id))} "Delete"]]
   [:div "Test Nav: " [:a {:href "/zoos/1"} "[1]"] " " [:a {:href "/zoos/2"} "[2]"]]
   [:div [:a {:href (rfe/href ::route-names/zoo-index)} "Back to list"]]])

(defn zoo-form [{:keys [id name]}]
  (let [form-state (r/atom {:name {:value name}})]
    (fn []
      [:form {:on-submit (fn [e]
                           (.preventDefault e)
                           (save-zoo id {:name (-> @form-state :name :value)}))}
       [:div
        [:div [:label {:for :name} "Name"]]
        [:div [:input {:type :text
                       :id :name
                       :value (-> @form-state :name :value)
                       :on-change #(swap! form-state (fn [fs] (assoc-in fs [:name :value] (-> % .-target .-value))))}]]]
       [:p
        [:button {:type :submit} "Save"]]])))

(defn detail-page []
  (let [id (-> @state/route-match :path-params :id)]
    [:<>
     [global-error-component]
     [:h3 "Zoo " id]
     ^{:key id}
     [zoo-loader id zoo-show]]))

(defn edit-page []
  (let [id (-> @state/route-match :path-params :id)]
    [:<>
     [global-error-component]
     [:h3 (str "Edit Zoo" id)]
     ^{:key id}
     [zoo-loader id zoo-form]]))

(defn create-page []
  [:<>
   [global-error-component]
   [:h3 "Create Zoo"]
   [zoo-form]])
