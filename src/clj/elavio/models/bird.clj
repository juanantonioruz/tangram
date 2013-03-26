(ns elavio.models.bird
  (:require [somnium.congomongo :as m]
            [modern-cljs.models.db :as db])
  )

(defn insert [bird]
    (db/maybe-init db/url_db )
    (m/insert! :users bird)
    )

(defn user-list []
  (db/maybe-init db/url_db)
  (m/fetch :users)
  )

(defn delete-all []
  (db/maybe-init db/url_db)
  (m/drop-coll! :users)
  )

