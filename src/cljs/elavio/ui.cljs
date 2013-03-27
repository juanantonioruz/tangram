(ns elavio.ui
  (:use
   [domina :only [by-id value set-styles! set-value! text attr]]
   [jayq.core :only [$ css inner hide show attr add-class remove-class fade-in fade-out]]
   ))

(defn message-to-user [message]
  (-> ($ :#messages) (inner message) (fade-in 300 (fn [] (fade-out ($ :#messages) 1000 (fn [] true)))))
  )

(defn init-loading []
  (-> ($ :body) (add-class :loading))
  )

(defn finish-loading
  ([] (-> ($ :body) (remove-class :loading)))
  ([message]
     (-> ($ :body) (remove-class :loading))
     (message-to-user message)
     )
  )

(defn interface-value-base [div new-value color]
(-> ($ (keyword (str "#" div)))
        (show)
        (css {:background color})        
        (inner new-value))
  )

(defn interface-value [new-value]
  (interface-value-base "interface" new-value "pink")
  )
(defn menu-value [new-value]
  (interface-value-base "menu" new-value "black")
  )

(defn hidden-results
  ([]
     (let [$interface ($ :#interface) ]
       (hide $interface)
       ))
  ([button-id new-text-button]
     (hidden-results)
     (-> ($ (keyword (str "#" button-id))) (attr "value" new-text-button))
     )
  )

(defn consola [message]
  (js/console.info message)
  )