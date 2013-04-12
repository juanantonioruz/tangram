(ns elavio.months
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


(defn bird-months [evt]
  (ui/init-loading)
  (let [bird-id (-> (current-target evt) (.getAttribute "bird"))]
    (remote-callback :find-months  [bird-id]
                     (fn [res]
                       
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "purple"})        
                           (inner (hiccups/html
                                   [
                                    :div
                                    [:a {:href "#" :bird bird-id :class :add-month} "add-month"] [:hr]
                                    [:table {:border 1 :width "100%"}
                                          [:tr [:th "month"] [:th "year"] [:th "paid"][:th "..."]]
                                          (for [x res]
                                            [:tr
                                             [:td (:month x)]
                                             [:td (:year x)]
                                             [:td (:paid x)]
                                             [:td
                                              [:a {:href "#" :month (:id x ) :class :edit-month} "edit"] " - "
                                              [:a {:href "#" :month (:id x ) :class :remove-month} "remove"] " - "
                                              ] 
                                             ])]
                                    ]
                                   )))
                       (ui/message-to-user (str "months found: " (count res)))
                                        ;(.datepicker ($ :.bird-date)
                                       ;)
                       (listen! (by-class :add-month) :click (fn [evt] (add-month evt)))
                       (listen! (by-class :edit-month) :click (fn [evt] (edit-month evt)))
                       (listen! (by-class :remove-month) :click (fn [evt] (remove-month evt)))
                       (ui/finish-loading)
                       ))

    )
  
  
  )

(defn add-month [evt]

  ( -> ($ :#tabs-1)
       (show)
       (css {:background "pink"})        
       (inner (hiccups/html
               [:div {:id "edit-month-div"}
                [:h1 "add month"]

                [:input {:type :hidden :id :bird-id :value (-> (current-target evt) (.getAttribute "bird"))} ]

                [:label "n-month"]
                [:input {:type :text :id :n-month :value ""} ]
                [:br]
                [:label "n-year"]
                [:input {:type :text :id :n-year :value ""} ]
                [:label "paid"]
                [:input {:type :check :id :paid :value false} ]

                [:br]
                [:input {:type :button :id :add-month :value "add-month"}]]
               )))
  (listen! (by-id :add-month) :click add-monti)
  
  )


(defn add-monti []
  ( ui/init-loading)
  (let [y (doall (map get-value [ "bird-id"  "n-month" "n-year" "paid"]))]
    (remote-callback :insert-month [y]
                     (fn [x]
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner "OLE"))
                       (ui/finish-loading "month inserted ok!")))
    )





  )

(defn get-value [id]
  (-> (by-id id) value)
  )
(defn edit-month [evt]
  (let [month-id (-> (current-target evt) (.getAttribute "month"))]
    (ui/init-loading)
    (remote-callback :find-month  [month-id]
                     (fn [x]
                       ( -> ($ :#tabs-1)
                            (show)
                            (css {:background "pink"})        
                            (inner (hiccups/html
                                    [:div {:id "edit-month-div"}
                                     [:h1 "Edit month"]
                                     (comment [:label "month-id"])
                                     [:input {:type :hidden :id :month-id :value (:id x) } ]
                                     (comment [:br])
                                     [:label "n-month"]
                                     [:input {:type :text :id :n-month :value (:month x) } ]
                                     [:br]
                                     [:label "n-year"]
                                     [:input {:type :text :id :n-year :value (:year x) } ]
                                     [:label "paid"]
                                     [:input {:type :check :id :paid :value (:paid x)} ]
                                     
                                     [:br]
                                     [:input {:type :button :id :update-month :value "update-month"}]]
                                    )))
                       (listen! (by-id :update-month) :click update-month)
                       (ui/finish-loading)      
                       
                       )
                     )
    

    )
  

  )

(defn update-month []
  ( ui/init-loading)
  (let [y (doall (map get-value [ "month-id" "n-month" "n-year"]))]
    (remote-callback :update-month [y]
                     (fn [x]
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner "OLE"))
                       (ui/finish-loading "month updated ok!")))
    )





  )

(defn confirm-remove-month []

  (ui/init-loading)
  (let [id (get-value "month-id")
        ]
    (remote-callback :remove-month [id]
                     (fn [x]
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner "permanently erased"))
                       (ui/finish-loading "month removed ok!")))
    )


  )

(defn remove-month [evt]

  (ui/init-loading)
  (let [month-id (-> (current-target evt) (.getAttribute "month"))]
    (remote-callback :find-month  [month-id]
                     (fn [x]
                       (-> ($ :#tabs-1)
                           (show)
                           (css {:background "pink"})        
                           (inner (hiccups/html
                                   [:div {:id "edit-month-div"}
                                    [:h1 "Are you sure?"]
                                    [:br]
                                    [:input {:type :hidden :id :month-id :value (:id x) } ]
                                    [:input {:type :button :id :confirm-remove-month :value "confirm-remove-month"}]]
            )))
                       (ui/message-to-user (str "month found " x))

                       (listen! (by-id :confirm-remove-month) :click confirm-remove-month)
                       (ui/finish-loading)

                       
                       
                       ))

    )
  
  
  )