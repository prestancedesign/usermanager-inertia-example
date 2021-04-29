;; copyright (c) 2021 Sean Corfield / Michaël Salihi, all rights reserved

(ns usermanager.controllers.user
  "The main controller for the user management portion of this app."
  (:require [inertia.middleware :as inertia]
            [ring.util.response :as resp]
            [usermanager.model.user-manager :as model]))

(defn reset-changes
  [_]
  (inertia/render "reset" {:message "The change tracker has been reset."}))

(defn default [_]
  (inertia/render "default" {:message (str "Welcome to the User Manager application demo!"
                                           "This uses just Reitit, Ring, and Selmer.")}))

(defn get-users
  "Render the list view with all the users in the addressbook."
  [req]
  (let [users (model/get-users (:db req))]
    (inertia/render "user-list" {:users users})))

(defn edit
  "Display the add/edit form.
  If the :id parameter is present, Compojure will have coerced it to an
  int and we can use it to populate the edit form by loading that user's
  data from the addressbook."
  [req]
  (let [db (:db req)
        user (when-let [id (get-in req [:path-params :id])]
               (model/get-user-by-id db id))]
    (inertia/render "user-form" {:user user
                                 :departments (model/get-departements db)})))

(defn save
  "This works for saving new users as well as updating existing users, by
  delegatin to the model, and either passing nil for :addressbook/id or
  the numeric value that was passed to the edit form."
  [req]
  (-> req
      :body-params
      (select-keys [:id :first_name :last_name :email :department_id])
      (->> (reduce-kv (fn [m k v] (assoc! m (keyword "addressbook" (name k)) v))
                      (transient {}))
           (persistent!)
           (model/save-user (:db req))))
  (resp/redirect "/user/list"))

(defn delete-by-id [req]
  (model/delete-user-by-id (:db req)
                           (get-in req [:path-params :id]))
  (resp/redirect "/user/list"))
