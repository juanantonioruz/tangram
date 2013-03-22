(ns elavio
  (:require 
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]]
            [domina.events :refer [listen! prevent-default]])
  (:use [domina :only [by-id value set-styles! set-value!]])
  )


(defn calculate  [quantity price tax discount]
  (remote-callback :calculate
                     [quantity price tax discount]
                     #(js/alert (.toFixed % 2))
    ))

(defn hidden-form []
  (set-styles! (by-id "loginForm")
               {:background-color "black"
                :color "white"})  )
(defn get-value [id]
  (-> (by-id id) value)
  )

(defn  other []
  (let [email (get-value "email")
        password (get-value "password")
        ]
    (js/alert email )

    (remote-callback :save [email password] #(js/alert %))
;;    (calculate 10 10 10 10)
    (hidden-form)

     
    )

  )

(defn ^:export init []
  ;; verify that js/document exists and that it has a getElementById
  ;; property
  (if (and js/document
           (.-getElementById js/document))
    (listen! (by-id "submit") :click other))
  )
