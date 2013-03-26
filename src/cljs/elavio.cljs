(ns elavio
  (:require 
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

(def user-name (atom ""))

(defn message-to-user [message]
  (-> ($ :#messages) (inner message) (fade-in 300 (fn [] (fade-out ($ :#messages) 1000 (fn [] true)))))
  )

(defn init-loading []
  (-> ($ :body) (add-class :loading))
  )

(defn -admin-loggin []
  (reset! user-name "ADMIN")
  (is-user-logged?)
  )


(defn finish-loading
  ([] (-> ($ :body) (remove-class :loading)))
  ([message]
     (-> ($ :body) (remove-class :loading))
     (message-to-user message)
     
     )

  )
(comment
  defn calculate  [quantity price tax discount]
  (remote-callback :calculate
                     [quantity price tax discount]
                     #(js/alert (.toFixed % 2))
    ))

(defn consoleta [message]
  (js/console.info message)
  )


(defn generate-html-user-list [rows]
  (hiccups/html [:ul {:class "foo"}
                 (for [x rows]
                   [:li (:mail x)])
                 ])
  )

(defn hidden-results []
(let [$interface ($ :#interface) ]
  (hide $interface)
  (-> ($ :#user-list) (attr "value" "show users"))
    )
  )

(defn show-users-results [res]
  (let [$interface ($ :#interface)
        html-user-list (generate-html-user-list res)
        html-delete-all-button (hiccups/html [:a {:href "#" :id :delete-all-users :value :delete-all-users} :delete-all-users])
        ]
    (-> $interface
        (show)
        (css {:background "pink"})        
        (inner (str html-delete-all-button html-user-list)))
    (-> ($ :#user-list) (attr "value" "hidde users"))
    )
  )
(defn print-users []
  (when (is-logged?)
    (init-loading)
  (remote-callback :user-list  []  (fn [x]  (show-users-results x)
                                     (finish-loading)(listen! (by-id :delete-all-users) :click -delete-all)) )
  )
  

  )
(defn -delete-all []
  (hidden-results)
  (remote-callback :user-delete-all  []
                   (fn [x]
                     (hidden-results)
                     (finish-loading)
                     (message-to-user "all users have been deleted now!")
                     ))
  )







(defn get-value [id]
  (-> (by-id id) value)
  )

(defn  send-new-user []
  (init-loading)
  (let [email (get-value "email")
        password (get-value "password")
        ]
    (remote-callback :save-new-user [email password] (fn [x] (finish-loading "user saved ok!")))
    )
  )

(defn show-users-event [evt]
  (let [text-button (-> ($ :#user-list) (attr "value" )) 
        searched (.search text-button "show")]
    (if (>= searched 0)
      (print-users)
      (hidden-results)
      )
    )
  )
(defn is-logged? []  (= @user-name "ADMIN"))
(defn is-user-logged? []
  (message-to-user (if-not (is-logged?) "Not logged yet!" (str "WELCOME: " @user-name)) )
  )

(defn listen-first-display []
  (listen! (by-id :logging-button) :click
           #((init-loading)
             (remote-callback :login [(get-value :user-name) (get-value :user-password)]
                              (fn [x]
                                (finish-loading (str "have you get logged?" x))
                                (if (== x true)
                                  (do
                                    (-admin-loggin)
                                     (print-users))
                                  nil)
                                )))


           )
  )
(defn first-display []
  (-> ($ :#interface)
        (show)
        (css {:background "pink"})        
        (inner (hiccups/html [
                              :div {:id "logging-frame"}
                              [:label "user-name"]
                              [:input {:type :text :id :user-name } ]
                              [:br]
                              [:label "user-password"]
                              [:input {:type :password :id :user-password } ]
                              [:br]
                              [:input {:type :button :id :logging-button :value "logging"}]
                                
                              ])))
  (listen-first-display)


  )

(defn ^:export init []
  ;; verify that js/document exists and that it has a getElementById
  ;; property
  (if (and js/document
           (.-getElementById js/document))
    (first-display)
    )

  ;       <input type="button" value="show users" id="user-list">    
  ;init function (j-query)
  )
(comment do
  (listen! (by-id :new-user) :click send-new-user)      
      (listen! (by-id :user-list) :click (fn [evt] (show-users-event evt)))
      (is-user-logged?)
             )