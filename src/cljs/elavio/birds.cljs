(ns elavio.birds
  (:require
   [shoreleave.remotes.http-rpc :refer [remote-callback]]
   [elavio.ui :as ui]
   [elavio.logging :as logging]
   [domina.events :refer [listen! prevent-default current-target target]])
  (:require-macros [hiccups.core :as hiccups])
  (:use
   [domina :only [by-id value set-styles! set-value! text attr]]
   [jayq.core :only [$ css inner hide show attr add-class remove-class fade-in fade-out]]
   )
  )

(defn generate-html-user-list [rows]
  (hiccups/html [:h1 "User List"] [:ul {:class "foo"}
                 (for [x rows]
                   [:li (str (:name x) "-" (:mail x)  "-" (:started x)  "-" (:ended x) ) ])
                 ])
  )

(defn show-users-results [res]
  (let [$interface ($ :#interface)
        html-user-list (generate-html-user-list res)
        html-delete-all-button (hiccups/html
                                [:a {:href "#" :id :delete-all-users :value :delete-all-users} :delete-all-users]
                                )
        html-new-user (hiccups/html [
                 :div {:id "new-bird-div"}
                                     [:h1 "insert new user"]
                                     [:label "bird-name"]
                                     [:input {:type :text :id :bird-name } ]
                                     [:br]
                                     [:label "bird-mail"]
                                     [:input {:type :text :id :bird-mail } ]
                                     [:br]
                                     [:label "bird-password"]
                                     [:input {:type :password :id :bird-password } ]
                                     [:br]
                                     [:label "bird-started"]
                                     [:input {:type :text :id :bird-started } ]
                                     [:br]
                                     [:label "bird-ended"]
                                     [:input {:type :text :id :bird-ended } ]
                                     [:br]
                                     [:input {:type :button :id :new-bird :value "new-bird"}]
                 ])
        ]
    (-> $interface
        (show)
        (css {:background "pink"})        
        (inner (str html-user-list    html-new-user "<hr>" html-delete-all-button)))
    (-> ($ :#user-list) (attr "value" "hidde users"))
    )
  (listen! (by-id :delete-all-users) :click delete-all-users)
  (listen! (by-id :new-bird) :click send-new-user)      
  )
(defn delete-all-users []
;  (hidden-results)
  (remote-callback :user-delete-all  []
                   (fn [x]
                     (ui/hidden-results)
                     (ui/finish-loading)
                     (ui/message-to-user "all users have been deleted now!")
                     ))
  )

(defn print-users []
  (ui/init-loading)
    (remote-callback :user-list  []
                     (fn [x]
                       (show-users-results x)
                       (ui/finish-loading)
                       ) )
    
  (comment when (logging/is-logged?)
    )
  )


(defn get-value [id]
  (-> (by-id id) value)
  )

(defn  send-new-user []
  (ui/init-loading)
  (let [name (get-value "bird-name")
        password (get-value "bird-password")
        mail (get-value "bird-mail")
        started (get-value "bird-started")
        ended (get-value "bird-ended")
        ]
    (remote-callback :save-new-user [name mail password started ended] (fn [x] (ui/finish-loading "user saved ok!")))
    )
  )


(defn info []
  (js/console.info "INFOOO working ns"))