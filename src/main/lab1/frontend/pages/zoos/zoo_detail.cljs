(ns lab1.frontend.pages.zoos.zoo-detail
  (:require [cljs-http.client :as http]
            [clojure.core.async :refer [<! go]]
            [lab1.frontend.route-names :as route-names]
            [lab1.frontend.state :as state]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))

; TODO: api error handling

(defn zoo-loader [id inner-component]
  (let [zoo (r/atom nil)]
    (go (let [url (str "http://localhost:3001/zoos/" id)
              response (<! (http/get url))]
          (reset! zoo (:body response))))
    (fn [id inner-component]
      (if-let [zoo @zoo]
        [inner-component zoo]
        [:div "Loading..."]))))

(defn update-zoo [id form-values]
  (go (let [url (str "http://localhost:3001/zoos/" id)
            response (<! (http/put url {:json-params form-values}))]
        (rfe/navigate ::route-names/zoo-detail {:path-params {:id id}}))))

(defn create-zoo [form-values]
  (go (let [url (str "http://localhost:3001/zoos")
            response (<! (http/post url {:json-params form-values}))]
        (rfe/navigate ::route-names/zoo-detail {:path-params {:id (-> response :body :id)}}))))

(defn save-zoo [id form-values]
  (if (= id "0")
    (create-zoo form-values)
    (update-zoo id form-values)))

(defn delete-zoo [id]
  (go (let [url (str "http://localhost:3001/zoos/" id)
            response (<! (http/delete url))]
        (rfe/navigate ::route-names/zoo-index))))

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
        [:label "Name"]
        [:input {:type :text
                 :value (-> @form-state :name :value)
                 :on-change #(swap! form-state (fn [fs] (assoc-in fs [:name :value] (-> % .-target .-value))))}]]
       [:div
        [:button {:type :submit} "Save"]]])))

(defn detail-page []
  (let [id (-> @state/route-match :path-params :id)]
    [:<>
     [:h3 "Zoo " id]
     ^{:key id}
     [zoo-loader id zoo-show]]))

(defn edit-page []
  (let [id (-> @state/route-match :path-params :id)]
    [:<>
     [:h3 (if (= id "0") "Create Zoo" (str "Edit Zoo" id))]
     ^{:key id}
     (if (= id "0")
       [zoo-form {:id "0"}]
       [zoo-loader id zoo-form])]))
