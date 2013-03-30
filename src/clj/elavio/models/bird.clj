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

(defn fetch-by-id
  "expects id as hexageximal part of the _id property, example: '#<ObjectId 515332ca036498c6823cdf70> you have to pass 515332ca036498c6823cdf70'"
  [id]
  (db/maybe-init db/url_db)
  (m/fetch-by-id :users (m/object-id id))

  )

(defn update [spec]
  (println (:id spec))
  (println (fetch-by-id (:id spec)))
  (m/update! :users (fetch-by-id (:id spec)) (merge (dissoc spec :id)) )

  )

(defn delete-all []
  (db/maybe-init db/url_db)
  (m/drop-coll! :users)
  )

