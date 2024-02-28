(ns lab1.frontend.pages.zoos.zoo-api
  (:require [cljs-http.client :as http]))

(def base-url "http://localhost:3001/zoos")

(defn get-zoos []
  (http/get base-url))

(defn get-zoo [id]
  (http/get (str base-url "/" id)))

(defn put-zoo [id data]
  (http/put (str base-url "/" id) {:json-params data}))

(defn post-zoo [data]
  (http/post base-url {:json-params data}))

(defn delete-zoo [id]
  (http/delete (str base-url "/" id)))
