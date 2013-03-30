(ns elavio
  (:require 
   [elavio.birds :as birds]
   [elavio.ui :as ui]
   [elavio.logging :as logging]

   [shoreleave.remotes.http-rpc :refer [remote-callback]]
   [clojure.string :refer [read-string]]
   [cljs.reader :refer [read-string]]
   [hiccups.runtime :as hiccupsrt]
   [domina.events :refer [listen! prevent-default current-target target]])
  (:require-macros [hiccups.core :as hiccups])
  (:use
   [domina :only [by-id value set-styles! set-value! text attr]]
   [jayq.core :only [$ css inner hide show attr add-class remove-class fade-in fade-out]]
   )
  )

(defn get-value [id]
  (-> (by-id id) value)
  )

(defn show-users-event [evt]
  (let [text-button (-> ($ :#user-list) (attr "value" )) 
        searched (.search text-button "show")]
    (if (>= searched 0)
      (birds/print-users)
      (ui/hidden-results "user-list"  "show users")
      )
    )
  )

(defn first-display []
  (ui/interface-value logging/loging-form)
  
  (listen! (by-id :logging-button) :click
           (defn on-logging-click []
             (ui/init-loading)
             (remote-callback :login [(get-value :user-name) (get-value :user-password)]
                              (fn [x]
                                (if (== x true)
                                  (do
                                    (ui/hidden-results)
                                    (logging/admin-login)
                                    (ui/menu-value (hiccups/html [
                                                                       :div {:id "ADMIN-ZONE"}
                                                                       [:input {:type :button :id :user-list :value "show users"}]                                                                       ]))
                                    (listen! (by-id :user-list) :click (fn [evt] (show-users-event evt)))
                                        ;(birds/print-users)

                                    
                                    )
                                  (ui/message-to-user "INVALID LOGIN!, TRY AGAIN"))
                                (ui/finish-loading)
                                )))
           )
  )

(defn ^:export init []
  ;; verify that js/document exists and that it has a getElementById
  ;; property
  (if (and js/document
           (.-getElementById js/document))
    (do
      (first-display)
      (.button ($ (keyword ":button")) )
      (logging/admin-login)
      ;    $( "#tabs" ).tabs();

      )
    
    )

  ;       <input type="button" value="show users" id="user-list">    
  ;init function (j-query)
  )


;;; COMMENTS 



(comment 
)
(comment do
  (listen! (by-id :new-user) :click send-new-user)      
      (listen! (by-id :user-list) :click (fn [evt] (show-users-event evt)))
      (logging/is-user-logged?)
      )

(comment
  defn calculate  [quantity price tax discount]
  (remote-callback :calculate
                     [quantity price tax discount]
                     #(js/alert (.toFixed % 2))
    ))