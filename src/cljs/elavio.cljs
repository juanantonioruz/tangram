(ns elavio
  (:require 
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [clojure.string :refer [read-string]]
            [cljs.reader :refer [read-string]]
            [hiccups.runtime :as hiccupsrt]
            [domina.events :refer [listen! prevent-default current-target target]])
  (:require-macros [hiccups.core :as hiccups])
  (:use [domina :only [by-id value set-styles! set-value! text attr]]
        [jayq.core :only [$ css inner hide show attr]]
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

(comment
user=> (html [:ul
               (for [x (range 1 4)]
                 [:li x])])
"<ul><li>1</li><li>2</li><li>3</li></ul>"

  )
(defn generate-html [rows]
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

(defn show-results [res]
  (let [$interface ($ :#interface)
        _html (generate-html res) ]
    (-> $interface
        (show)
        (css {:background "blue"})        
        (inner _html))
    (-> ($ :#users) (attr "value" "hidde users"))
    )
  )

(defn print-users []
  (remote-callback :user-list [] #(show-results % ))
  )

(defn j-query []
  (let [
        $interface ($ :#interface)]
    (-> $interface
        (css {:background "blue"})
        (inner (hiccups/html [:span {:class "foo"} "bar"])))
    )
  )


(defn hidden-form []
  (set-styles! (by-id "loginForm")
               {:background-color "black"
                :color "white"})  )
(defn get-value [id]
  (-> (by-id id) value)
  )

(defn  send-new-user []
  (let [email (get-value "email")
        password (get-value "password")
        ]
    (js/alert email)
    (remote-callback :save-new-user [email password] #(js/alert  (str "returning remote result: " %)))
;;    (calculate 10 10 10 10)
    (hidden-form)    
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
  ;  (listen! (by-id "submit") :click send-new-user)
    (listen! (by-id "users") :click (fn [evt] (show-users-event evt)) 
             )
    

    )
  ;init function (j-query)
  )
