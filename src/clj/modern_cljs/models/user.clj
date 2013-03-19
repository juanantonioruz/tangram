(ns modern-cljs.models.user
  (:require [somnium.congomongo :as m]
            [modern-cljs.models.db :as db])
  )

(defn insert [user]
    (db/maybe-init db/url_db )
    (m/insert! :users user)
    )

(defn user-list []
  (db/maybe-init db/url_db)
  (m/fetch :users)
 )

