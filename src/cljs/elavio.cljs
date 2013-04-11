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
   [domina.events :refer [listen! prevent-default current-target target]])
  (:require-macros [hiccups.core :as hiccups])
  (:use
   [clojure.string :only [join]]
   [c2.event :only [on-load]]
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

(def visualization {:width 500 :height 500})



(defn init-app [users]
  
  (comment js/console.info (clojure.string/join ", "  (map #(:name %) users)))
  ( let [users-count (count users)
         svg (dom/select "#the_svg")
         mappingRects (fn [init u]
                        (let [{:keys [name mail]} u]
                          (dom/parent (dom/append! init [:rect {:x 0 :y 0 :width 40 :height 40 :stroke "black" :stroke-width 2 :fill "red" :name name :mail mail}])))
                        )
         modified-svg (reduce mappingRects  svg users)
         
         ]
    (comment js/console.dir (str "result " (clojure.string/join ", " (map #(dom/attr % :name)  (dom/select-all "rect")))))
    (comment js/console.dir (str "result with ->" (->> (dom/select-all "rect") (map #(dom/attr % :name) ) (clojure.string/join ",")) ))

    
(comment js/console.dir (dom/attr  (dom/select "rect") :name ))
    (let [margin-ext 50
          margin-int 20
          width-available ( - (:width visualization) (* 2 margin-ext) (* margin-int (dec users-count)))
          rect-width (/ width-available users-count)
          x-positions (reduce (fn [init u] (conj init (+ margin-ext  (* margin-int (count init)) (*  rect-width (count init))))) [] (dom/select-all "rect") )
          ]
      (doall (map (fn [a b]  (dom/attr b :x a) "joe") x-positions (dom/select-all "rect")))

      )
    
    
    
    
    (comment js/console.dir (dom/select (str "#" (dom/attr  svg :id))))
    (dom/attr (dom/select "#the_svg") :width 1000)
    )

  )

(on-load

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