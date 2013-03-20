(ns elavio.remotes
  (:require
   [modern-cljs.core :refer [handler]]
   [compojure.handler :refer [site]]
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]))

(defremote calculate [quantity price tax discount]
  (println (str "calculating: quantity" quantity ))
  (println (str "calculating: price" price ))
  (println (str "calculating: tax" tax ))
  (println (str "calculating: discount" discount))
  (-> (* quantity price)
      (* (+ 1 (/ tax 100)))
      (- discount)))


(def app (-> (var handler)
             (wrap-rpc)
             (site)))
