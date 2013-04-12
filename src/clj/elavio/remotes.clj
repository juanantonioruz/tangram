(ns elavio.remotes
  (:use
   [ring.middleware.session] )
  (:require
   [somnium.congomongo :as m]
   [elavio.models.bird :as bird]
   [elavio.models.month :as month]
   [elavio.mailing :as mailing]
   [modern-cljs.core :refer [handler]]
   [compojure.handler :refer [site]]
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))


(defremote save-new-user [name mail password started ended]
  (:mail (bird/insert {:mail mail :password password :name name :started started :ended ended}))
  )

(defremote update-user [id name mail password started ended]
  (:mail (bird/update-bird {:id id :mail mail :password password :name name :started started :ended ended}))
  )

(defremote remove-user [id]
  (:mail (bird/remove-bird id))
  )

(defremote mail-data-user [id]
  (let [bird (bird/fetch-by-id id)]
    (:error (mailing/mailto (:mail bird) "your data!" "hello bird!"))
    )
  )


(defremote login [name pass]
  (if (and (= pass "jaruz") (= name "jaruz"))
    true
    false
    )
  )

(defn get-id-string [item]
  (first (clojure.string/split (str (:_id item)) #" "))
  )

(defn transform-ui [item]
  {:id (get-id-string item)   :mail (:mail item) :password (:password item) :name (:name item) :ended (:ended item) :started (:started item)}
  )

(defremote user-list []
  (let [users (bird/user-list )]
                                        ;    (println )
                                        ;(first )
    ;(println (-> (first users)  (:_id) (class)))
    ;(println (-> (m/object-id "515332ca036498c6823cdf70")))
;    (println (-> (bird/fetch-by-id (m/object-id "515332ca036498c6823cdf70"))))
    (reduce (fn [col-returned item] (conj col-returned (transform-ui item))) [] users)    
    )
  )

(defremote find-user [id]
  (transform-ui (bird/fetch-by-id id))
  )

(defremote find-month [id]
  (println id)
  (let [m (month/fetch-by-id id)]
    (dissoc (assoc m :id (get-id-string m)) :_id :bird)
    )
  )

(defremote update-month [[id month  year :as v] ]
  (month/update id month year )
  true

  )

(defremote find-months [id]

  (let [the-bird (bird/fetch-by-id id)
        months (month/months-bird the-bird)]
    (doall (map (fn [o]
                  (dissoc (assoc o :id (get-id-string o)) :_id :bird)
                  ) months))
    
    )
  )


(defremote user-delete-all []
  (bird/delete-all)
  )



(defremote calculate [quantity price tax discount]

  (println (str "calculating: quantity" quantity ))
  (println (str "calculating: price" price ))
  (println (str "calculating: tax" tax ))
  (println (str "calculating: discount" discount))
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount))
  )


(def app (-> (var handler)
;             (wrap-session)
             (wrap-rpc)
             (site)))
