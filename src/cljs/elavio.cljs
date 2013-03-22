(ns elavio
  (:require 
            [shoreleave.remotes.http-rpc :refer [remote-callback]]
            [cljs.reader :refer [read-string]])
  (:use [domina :only [by-id value]])
  )


(defn calculate  [quantity price tax discount]
  (remote-callback :calculate
                     [quantity price tax discount]
                     #(js/alert (.toFixed % 2))
  ( js/alert "Hello, ClojureScript!")
 
  (str "")))

(defn ^:export other []
  (str "ey" (-> (by-id "email") value)))

