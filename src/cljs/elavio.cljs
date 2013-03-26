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
   [jayq.core :only [$ css inner hide show attr add-class remove-class]]
   )
  )

(defn init-loading []
  (-> ($ :body) (add-class :loading))
  )

(defn finish-loading
  ([] (-> ($ :body) (remove-class :loading)))
  ([message]
     (-> ($ :body) (remove-class :loading))
     (-> ($ :#messages) (inner message))
     
     )

  )
(defn calculate  [quantity price tax discount]
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
  (-> ($ :#users) (attr "value" "show users"))
    )
  )

(defn show-users-results [res]
  (let [$interface ($ :#interface)
        html-user-list (generate-html-user-list res)
        html-delete-all-button (hiccups/html [:input {:type "button" :id :delete-all-users :value :delete-all-users}])
        ]
    (-> $interface
        (show)
        (css {:background "pink"})        
        (inner (str html-delete-all-button html-user-list)))
    (-> ($ :#users) (attr "value" "hidde users"))
    )
  )
(defn print-users []
  (init-loading)
  (remote-callback :user-list  []  (fn [x]  (show-users-results x)
                                   (finish-loading)(listen! (by-id :delete-all-users) :click -delete-all)) )
  )
(defn -delete-all []
  (hidden-results)
  (remote-callback :user-delete-all  []  (fn [x]  (print-users)
                                   (finish-loading)) )
  )



;
(defn j-query []
  (let [
        $interface ($ :#interface)]
    (-> $interface
        (css {:background "blue"})
        (inner (hiccups/html [:span {:class "foo"} "bar"])))
    )
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
  (let [text-button (-> ($ :#users) (attr "value" )) 
        searched (.search text-button "show")]
    (if (>= searched 0)
      (print-users)
      (hidden-results)
      )
    
    )
  )

(defn ^:export init []
  ;; verify that js/document exists and that it has a getElementById
  ;; property
  (if (and js/document
           (.-getElementById js/document))
    (do
      (listen! (by-id "submit") :click send-new-user)
      (listen! (by-id "users") :click (fn [evt] (show-users-event evt)) 
)
             )
    

    )
  ;init function (j-query)
  )
