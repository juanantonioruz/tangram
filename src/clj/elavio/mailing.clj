(ns elavio.mailing
  (:use [postal.core :as postal])

  )

(defn mailto [target subject body]
  (try
    (postal/send-message ^{:host "smtp.sendgrid.net"
                         :user (System/getenv "SENDGRID_USERNAME")
                         :pass (System/getenv "SENDGRID_PASSWORD")}
                       {:from "group@professor-sori.com"
                        :to target
                        :subject subject
                        :body body})
    (catch Exception e (println (.getMessage e)) {:error true})
    (finally {:error true})
    )
  )







