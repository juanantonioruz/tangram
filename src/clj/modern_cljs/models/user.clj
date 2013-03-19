(ns modern-cljs.db.user
  (:require [somnium.congomongo :as m])
  (:use [modern-cljs.models.db :as db])
  )

(defn ey [user]
    (db/maybe-init url_db )
    (m/insert! :users user)

  )

