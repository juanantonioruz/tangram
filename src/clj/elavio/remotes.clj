(ns elavio.remotes
  (:use
   [ring.middleware.session] )
  (:require
   [somnium.congomongo :as m]
   [elavio.models.bird :as bird]
   [modern-cljs.core :refer [handler]]
   [compojure.handler :refer [site]]
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))


(defremote save-new-user [name mail password started ended]
  (:mail (bird/insert {:mail mail :password password :name name :started started :ended ended}))
  )

(defremote update-user [id name mail password started ended]
  (:mail (bird/update {:id id :mail mail :password password :name name :started started :ended ended}))
  )
(defremote login [name pass]
  (if (and (= pass "jaruz") (= name "jaruz"))
    true
    false
    )
  )

(defn transform-ui [item]
  {:id (first (clojure.string/split (str (:_id item)) #" "))  :mail (:mail item) :password (:password item) :name (:name item) :ended (:ended item) :started (:started item)}
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
