(ns elavio
  (:use-macros [c2.util :only [bind!]])
  (:require
   [elavio.birds :as birds]
   [elavio.ui :as ui]
   [elavio.logging :as logging]
   [c2.dom :as dom]
   [shoreleave.remotes.http-rpc :refer [remote-callback]]
   [clojure.string :refer [read-string]]
   [cljs.reader :refer [read-string]]
   [hiccups.runtime :as hiccupsrt]
   [c2.event :as c2-event]
   [domina.events :refer [listen! prevent-default current-target target]])
  (:require-macros [hiccups.core :as hiccups])
  (:use
   [clojure.string :only [join]]

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

(defn ^:export d3-integration []
  (logging/admin-login)
  (js/console.info "d3_integration")
  )

(def visualization {:width 500 :height 500 :margin-ext 50  :margin-int 20})

(defn get-x-width-positions [visualization elements-count]
   
  (let [{:keys [width margin-ext margin-int]} visualization
          width-available ( - width (* 2 margin-ext) (* margin-int (dec elements-count)))
          rect-width (/ width-available elements-count)           
          ]
     ( reduce (fn [init u] (conj init [(+ margin-ext  (* margin-int (count init)) (*  rect-width (count init))) rect-width])) [] (repeat elements-count nil))

      )
  
  )

(defn my-transition [x]
  {:-webkit-transform (str "translateX(" x "px)")
   :transition-duration "0.5s"
   :transition-timing-function "ease"})


(defn init-app [users]
(dom/attr (dom/select "#the_svg") :width 500)

( let [users-count (count users)
         svg (dom/select "#the_svg")
         append-user-rect (fn [u geo]
                        (let [{:keys [name mail]} u
                               [x width] geo
                              ]
                          (dom/parent
                           (dom/append!
                            svg
                            [:rect {:x x :class "hola" :y 0 :width width :height 40 :stroke "black" :stroke-width 2 :fill "red" :name name :mail mail}])))
                        )
       geo-positions (get-x-width-positions visualization users-count)

         ]
  (doall (map append-user-rect  users geo-positions))
  
  (doseq [raw (dom/select-all ".hola")]
   (c2-event/on-raw  raw :mouseover
                     (fn [ e]
                       (-> (dom/select "circle" )

                           (dom/style {:fill "blue" :-webkit-transform "translateX(300px)" :-webkit-transition "all 900ms cubic-bezier(0.740, 0.265, 0.250, 0.720)"}) 
)
                       (js/console.dir (->(.-target e) (dom/attr :mail) ))
                       ))
(c2-event/on-raw  raw :click
                     (fn [ e]
                       (-> (dom/select "circle" ) (dom/style {:fill "blue" :-webkit-transform "translateX(0px)" :-webkit-transition "all 900ms cubic-bezier(0.740, 0.265, 0.250, 0.720)"}) )
                       
                       (let [el (.-target e)
                             mail (:mail (dom/attr el))]
                         (dom/append! "#the_svg" [:text {:value "hola" :y (dom/attr (dom/select "circle") :cy) :x 0 :color "black"} "hola"])
                         )
                       (js/console.dir (->(.-target e) (dom/attr :mail) ))
                       ))
   )
       )



  )

(c2-event/on-load
 #(birds/c2-get-users init-app)
 
 )
                                        ;
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