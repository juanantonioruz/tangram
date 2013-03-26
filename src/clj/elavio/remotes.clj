(ns elavio.remotes
  (:use
   [ring.middleware.session] )
  (:require
   [elavio.models.bird :as bird]
   [modern-cljs.core :refer [handler]]
   [compojure.handler :refer [site]]
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))


(defremote save-new-user [mail password]
  (:mail (bird/insert {:mail mail :password password}))

  )

(defremote login [name pass]
  (if (and (= pass "jaruz") (= name "jaruz"))
    true
    false
    )
  )


(defremote user-list []
  (let [users (bird/user-list )]
    (reduce (fn [col-returned item] (conj col-returned {:mail (:mail item) :password (:password item)})) [] users)    
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
