(ns elavio.remotes
  (:require
   [elavio.models.bird :as bird]
   [modern-cljs.core :refer [handler]]
   [compojure.handler :refer [site]]
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))


(defremote save [mail password]
  (:mail (bird/insert {:mail mail :password password}))

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
             (wrap-rpc)
             (site)))
