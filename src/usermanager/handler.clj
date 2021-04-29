(ns usermanager.handler
  (:require [inertia.middleware :as inertia]
            [reitit.ring :as ring]
            [reitit.ring.middleware.parameters :as parameters]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [selmer.parser :as html]
            [usermanager.controllers.user :as user-ctl]))

(def asset-version "1")

(defn template [data-page]
  (html/render-file "layouts/default.html" {:page data-page}))

(def middleware-db
  {:name ::db
   :compile (fn [{:keys [db]} _]
              (fn [handler]
                (fn [req]
                  (handler (assoc req :db db)))))})

(defn app [db]
  (ring/ring-handler
   (ring/router
    [["/" {:handler user-ctl/default}]
     ["/reset" {:handler user-ctl/reset-changes}]
     ["/user"
      ["/list" {:handler user-ctl/get-users}]
      ["/form" {:handler user-ctl/edit}]
      ["/form/:id" {:get {:parameters {:path {:id int?}}}
                    :handler user-ctl/edit}]
      ["/save" {:post {:handler user-ctl/save}}]
      ["/delete/:id" {:get {:parameters {:path {:id int?}}}
                      :handler user-ctl/delete-by-id}]]]
    {:data {:db db
            :middleware [parameters/parameters-middleware
                         wrap-keyword-params
                         middleware-db
                         [inertia/wrap-inertia template asset-version]]}})
   (ring/routes
    (ring/create-resource-handler
     {:path "/"})
    (ring/create-default-handler
     {:not-found (constantly {:status 404 :body "Not found"})}))))
