(ns modern-cljs.core
  (:use [compojure.core] )
  (:require [compojure.handler :as handler][ring.adapter.jetty :as jetty]
            [compojure.route :as route]))

;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.

(defroutes app-routes
  ; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  (GET "/aa" [] "<p>Hellaaaao from compojure</p>")

  ; to server static pages saved in resources/public directory
  (route/resources "/pub")
  ; if page is not found
  (route/not-found "Page non found"))


;; site function create an handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:
(def handler
  (handler/site app-routes))

(defn -main [& m ]
  (let [ port (Integer. (get (System/getenv) "PORT" "8080"))]
    (jetty/run-jetty handler {:port port })
    )
  
  )


