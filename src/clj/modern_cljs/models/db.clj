(ns  modern-cljs.models.db
  (:use somnium.congomongo)
  (:use [somnium.congomongo.config :only [*mongo-config*]]))

;(def url_db "mongodb://root:root@127.0.0.1:27017/db")
(def url_db (get (System/getenv) "MONGOHQ_URL"))

 ;; Construct an options map.
(defn split-mongo-url [url]
  "Parses mongodb url from heroku, eg. mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)] ;; Setup the regex.
    (when (.find matcher) ;; Check if it matches.
      (zipmap [:match :user :pass :host :port :db] (re-groups matcher)))))

(defn maybe-init [MONGOHQ_URL]
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*)) ;; If global connection doesn't exist yet.
    (let [mongo-url MONGOHQ_URL
	  config    (split-mongo-url mongo-url)] ;; Extract options.
      (println "Initializing mongo @ " mongo-url)
      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config))) ;; Setup global mongo.
      (authenticate (:user config) (:pass config)) ;; Setup u/p.
      ;; Create collection named 'firstcollection' if it doesn't exist.
      (or (collection-exists? :firstcollection) (create-collection! :firstcollection))
      (or (collection-exists? :users) (create-collection! :users))
      )))



(defn default-connect []
  (maybe-init url_db)
  )


;(split-mongo-url "mongodb://root:root@127.0.0.1:27017/db")

;(with-mongo conn
;  (insert! :robots {:name "robby"}))