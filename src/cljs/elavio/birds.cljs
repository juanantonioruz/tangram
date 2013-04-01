(ns elavio.birds
  (:require
   [shoreleave.remotes.http-rpc :refer [remote-callback]]
   [elavio.ui :as ui]
   [elavio.logging :as logging]
   [domina.events :refer [listen! prevent-default current-target target]])
  (:require-macros [hiccups.core :as hiccups])
  (:use
   [domina :only [by-id by-class value set-styles! set-value! text attr]]
   [jayq.core :only [$ css inner hide show attr add-class remove-class fade-in fade-out]]
   )
  )

(defn show-users-results [res]
  (let [$interface ($ :#interface)
        html-user-list  [
                         :div [:table {:border 1 :width "100%"}
                               [:tr [:th "name"] [:th "mail"][:th "started"][:th "ended"][:th "..."]]
                          (for [x res]
                            [:tr
                             [:td (:name x)]
                             [:td (:mail x)]
                             [:td (:started x)]
                             [:td (:ended x)]
                             [:td
                              [:a {:href "#" :bird (:id x ) :class :edit-bird} "edit"] " - "
                              [:a {:href "#" :bird (:id x ) :class :remove-bird} "remove"] " - "
                              [:a {:href "#" :bird (:id x ) :class :mail-data} "data mailing"]
                              ] 

                             ])]
                         (comment [:ul {:class "foo"}
                           (for [x res]
                             [:li (str  (:name x) "-" (:mail x)  "-" (:started x)  "-" (:ended x) ) [:a {:href "#" :bird (:id x ) :class :edit-bird} "edit"]])
                           ])
                         ] 
        html-delete-all-button [:a {:href "#" :id :delete-all-users :value :delete-all-users} :delete-all-users]
        html-new-user [
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
                                     [:input {:type :text :id :bird-started :class :bird-date} ]
                                     [:br]
                                     [:label "bird-ended"]
                                     [:input {:type :text :id :bird-ended :class :bird-date} ]
                                     [:br]
                                     [:input {:type :button :id :new-bird :value "new-bird"}]
                       ]        
        inner-html (hiccups/html
                    [:div {:id "tabs"}
                     [:ul
                      [:li [:a {:href "#tabs-1" :id :user-list} "User List"]]
                      [:li [:a {:href "#tabs-2"} "New User"]]
                      [:li [:a {:href "#tabs-3"} "Delete All Users"]]
                      ]
                     [:div {:id "tabs-1"} html-user-list]
                     [:div {:id "tabs-2"} html-new-user]
                     [:div {:id "tabs-3"} html-delete-all-button]
                     ]
                    )
        ]
    (-> $interface
        (show)
        (css {:background "pink"})        
        (inner inner-html))
    (-> ($ :#user-list) (attr "value" "hidde users"))
    )
  (listen! (by-id :delete-all-users) :click delete-all-users)
  (listen! (by-id :new-bird) :click send-new-user)
  (listen! (by-class :edit-bird) :click (fn [evt] (edit-bird evt)))
  (listen! (by-class :remove-bird) :click (fn [evt] (remove-bird evt)))
  (listen! (by-class :mail-data) :click (fn [evt] (mail-data-bird evt)))
  (listen! (by-id :user-list) :click print-users)

  (.tabs ($ :#tabs) )
  (.datepicker ($ :.bird-date) )
  
  )

(defn remove-bird [evt]

    (ui/init-loading)
  (let [bird-id (-> (current-target evt) (.getAttribute "bird"))]
    (remote-callback :find-user  [bird-id]
                     (fn [x]
                           
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner (hiccups/html
                                   [:div {:id "edit-bird-div"}
                                    [:h1 "Are you sure?"]
                                    [:br]
                                     [:input {:type :hidden :id :bird-id :value (:id x) } ]
                                     [:input {:type :button :id :confirm-remove-bird :value "confirm-remove-bird"}]]
                                   (comment [:p (str "user found" x)]))))
                       (ui/message-to-user (str "user found" x))
                       (.datepicker ($ :.bird-date) )
                       (listen! (by-id :confirm-remove-bird) :click confirm-remove-bird)
                       (ui/finish-loading)

                       
                       
                     ))

)
  
  
  )

(defn mail-data-bird [evt]

    (ui/init-loading)
  (let [bird-id (-> (current-target evt) (.getAttribute "bird"))]
    (remote-callback :mail-data-user  [bird-id]
                     (fn [x]
                       (let [message (if-not (= x :success) "problem sending mail, contact to webmaster" "data sended correctly")]
                        (-> ($ :#tabs-1)
                            (show)
                            (css {:background "pink"})        
                            (inner (hiccups/html
                                    [:div 
                                     [:h1 message]
                                     [:br]]
                                    )))
                        (ui/message-to-user message)
                        (ui/finish-loading))
                     ))

)

  
  
  )




(defn edit-bird [evt]

    (ui/init-loading)
  (let [bird-id (-> (current-target evt) (.getAttribute "bird"))]
    (remote-callback :find-user  [bird-id]
                     (fn [x]
                           
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner (hiccups/html
                                   [:div {:id "edit-bird-div"}
                                    [:h1 "Edit user"]
                                    (comment [:label "bird-id"])
                                    [:input {:type :hidden :id :bird-id :value (:id x) } ]
                                    (comment [:br])
                                    [:label "bird-name"]
                                     [:input {:type :text :id :bird-name :value (:name x) } ]
                                     [:br]
                                     [:label "bird-mail"]
                                     [:input {:type :text :id :bird-mail :value (:mail x) } ]
                                     [:br]
                                     [:label "bird-password"]
                                     [:input {:type :password :id :bird-password :value "xxx" } ]
                                     [:br]
                                     [:label "bird-started"]
                                     [:input {:type :text :id :bird-started :class :bird-date :value (:started x)} ]
                                     [:br]
                                     [:label "bird-ended"]
                                     [:input {:type :text :id :bird-ended :class :bird-date :value (:ended x)}  ]
                                     [:br]
                                     [:input {:type :button :id :update-bird :value "update-bird"}]]
                                   (comment [:p (str "user found" x)]))))
                       (ui/message-to-user (str "user found" x))
                       (.datepicker ($ :.bird-date) )
                       (listen! (by-id :update-bird) :click update-bird)
                       (ui/finish-loading)

                       
                       
                     ))

    (js/console.dir (-> (current-target evt) (.getAttribute "bird"))))
  
  
  )


(defn confirm-remove-bird []

  (ui/init-loading)
  (let [id (get-value "bird-id")
        ]
    (remote-callback :remove-user [id]
                     (fn [x]
                         (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner "permanently erased"))
                         (ui/finish-loading "user removed ok!")))
    )


  )

(defn update-bird []

  (ui/init-loading)
  (let [name (get-value "bird-name")
        password (get-value "bird-password")
        mail (get-value "bird-mail")
        started (get-value "bird-started")
        ended (get-value "bird-ended")
        id (get-value "bird-id")
        ]
    (remote-callback :update-user [id name mail password started ended]
                     (fn [x]
                         (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner "OLE"))
                         (ui/finish-loading "user updated ok!")))
    )


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
  (js/console.info "INFOOO working ns"));; This buffer is for notes you don't want to save, and for Lisp evaluation.


          


  
