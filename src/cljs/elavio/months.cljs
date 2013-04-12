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
                                    :div [:table {:border 1 :width "100%"}
                                          [:tr [:th "month"] [:th "year"][:th "..."]]
                                          (for [x res]
                                            [:tr
                                             [:td (:month x)]
                                             [:td (:year x)]
                                             [:td
                                              [:a {:href "#" :month (:id x ) :class :edit-month} "edit"] " - "
                                              [:a {:href "#" :month (:id x ) :class :remove-month} "remove"] " - "
                                              ] 
                                             ])]
                                    ]
                                   )))
                       (ui/message-to-user (str "months found: " (count res)))
                                        ;(.datepicker ($ :.bird-date) )
                       (listen! (by-class :edit-month) :click (fn [evt] (edit-month evt)))
                       (ui/finish-loading)
                       ))

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
