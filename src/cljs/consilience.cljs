(ns consilience
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


(def visualization {:width 500 :height 500 :margin-ext 50  :margin-int 20})

(defn get-x-width-positions [visualization elements-count]
  
  (let [{:keys [width margin-ext margin-int]} visualization
        width-available ( - width (* 2 margin-ext) (* margin-int (dec elements-count)))
        rect-width (/ width-available elements-count)           
        ]
    (reduce (fn [init u] (conj init [(+ margin-ext  (* margin-int (count init)) (*  rect-width (count init))) rect-width])) [] (repeat elements-count nil))
    )
  )

(defn my-transition [x]
  {:-webkit-transform (str "translateX(" x "px)")
   :transition-duration "0.5s"
   :transition-timing-function "ease"})



(defn append-user-rect [svg color y]
  (fn [u geo]
    (let [{:keys [name mail id]} u
          [x width] geo
          ]
      (dom/parent
       (dom/append!
        svg
        [:rect {:x x :class :user_rect :y y :width width :height 40 :stroke "black" :stroke-width 2 :fill color :user-id id :name name :mail mail}])))
    )
  )





(defn init-app [users]
  (dom/attr (dom/select "#the_svg") :width 500)

  ( let [users-count (count users)
         svg (dom/select "#the_svg")
         append-user-rectt (append-user-rect svg "red" 20 )
         geo-positions (get-x-width-positions visualization users-count)
         ]
    (doall (map append-user-rectt  users geo-positions))

    (doseq [raw (dom/select-all ".user_rect")]

      (c2-event/on-raw  raw :click
                        (fn [ e]
                          ( remote-callback :find-months [(dom/attr (.-target e) :user-id)]
                                            ( fn [res]
                                              (let [geo2-positions (get-x-width-positions visualization (count res))
                                                   append-month-rect (append-user-rect "#detail" "pink" 200 )
                                                   ]
                                                (dom/replace! "#detail" [:g {:id "detail"}])
                                                (doall (map append-month-rect  res geo2-positions))
                                               )
                                              ))
                          ))
      (c2-event/on-raw  raw :mouseover
                        (fn [ e]
                          (let [el (.-target e)
                                mail (:mail (dom/attr el))]
                            (-> (dom/select "circle" )
                                (dom/style {:fill "blue" :-webkit-transform "translateX(300px)" :-webkit-transition "all 900ms cubic-bezier(0.740, 0.265, 0.250, 0.720)"}) 
                                )
                            (dom/append! "#the_svg"
                                         [:text
                                          { :id  "mail"
                                           :y (dom/attr (dom/select "circle") :cy)
                                           :x 0
                                           :color "black"} mail])
                            )
                          
                          ))
      (c2-event/on-raw  raw :mouseout
                        (fn [ e]
                          (-> (dom/select "circle" ) (dom/style {:fill "blue" :-webkit-transform "translateX(0px)" :-webkit-transition "all 900ms cubic-bezier(0.84, 0.265, 0.250, 0.720)"}) )
                          (let [el (.-target e) ]
                            (when-let [el (dom/select "#mail")]
                              (dom/remove! el))
                            )
                          ))
      )
    )

  )