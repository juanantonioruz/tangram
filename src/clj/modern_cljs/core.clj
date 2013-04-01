(ns modern-cljs.core
  (:use [compojure.core][ring.middleware.session]  )
  (:require
   [compojure.handler :as handler]
   [ring.util.response :as resp]
   [ring.adapter.jetty :as jetty]
   [compojure.route :as route]
   [modern-cljs.models.user :as user]))

;; defroutes macro defines a function that chains individual route
;; functions together. The request map is passed to each function in
;; turn, until a non-nil response is returned.

(defroutes app-routes
  ; to serve document root address
  (GET "/" [] (resp/redirect "/pub/simple.html"))
  (GET "/jor" [] "<p>JOR</p>")
  (GET "/insert-user/:name" [name]
       (user/insert {:name name})
      
       (str "<p>Inserted:" name "</p>" (user/user-list))
       
       )
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
(defn start []
  (jetty/run-jetty handler {:port 5000 }))


