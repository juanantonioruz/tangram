(ns elavio.models.month
  (:require [somnium.congomongo :as m]
            [modern-cljs.models.db :as db])
  )


(defn remove [id]
  (db/maybe-init db/url_db)
  (let [_id (m/object-id id)]
    (m/destroy! :months {:_id _id})
    )
  
  
 ; (println )
  )

(defn months-list []
  (db/maybe-init db/url_db)
  (m/fetch :months)
  )
(defn months-bird [bird]
  (db/maybe-init db/url_db)
  (m/fetch :months :where {:bird (:_id bird)})
  )
(defn insert-month [bird month year paid]
    (db/maybe-init db/url_db )
    (m/insert! :months {:month month :year year :bird (:_id bird) :paid paid})
    )

(defn fetch-by-id
  "expects id as hexageximal part of the _id property, example: '#<ObjectId 515332ca036498c6823cdf70> you have to pass 515332ca036498c6823cdf70'"
  [id]
  (db/maybe-init db/url_db)
  (m/fetch-by-id :months (m/object-id id))

  )
(defn update [id month year paid]
  (db/maybe-init db/url_db)
  (let [monti (fetch-by-id id)]
    (m/update! :months monti (assoc monti :month month :year year :paid paid) )
    )
  

  )