(ns elavio.logging
  
  (:require [elavio.ui :as ui])
  (:require-macros [hiccups.core :as hiccups]))

(def user-name (atom ""))

(defn is-logged? []  (= @user-name "ADMIN"))
(defn is-user-logged? []
  (ui/message-to-user (if-not (is-logged?) "Not logged yet!" (str "WELCOME: " @user-name)) )
  )
(defn log-out []
  (reset! user-name "")
  (is-user-logged?)
  )
(defn admin-login []
  (reset! user-name "ADMIN")
  (is-user-logged?)
  )

(defn loging-form []
  (hiccups/html [
                 :div {:id "logging-frame"}
                 [:label "user-name"]
                 [:input {:type :text :id :user-name } ]
                 [:br]
                 [:label "user-password"]
                 [:input {:type :password :id :user-password } ]
                 [:br]
                 [:input {:type :button :id :logging-button :value "logging"}]
                 ]))