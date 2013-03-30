(ns elavio.mailing

  )
(comment (defn mail [& m]
   (let [mail (apply hash-map m)
         props (java.util.Properties.)
         SMTP_HOST_NAME "smtp.sendgrid.net"]

     (doto props

       (.put "mail.transport.protocol" "smtp")
       (.put "mail.smtp.host" SMTP_HOST_NAME)
       (.put "mail.smtp.port" 587)
       (.put "mail.smtp.user" (:user mail))
       (.put "mail.smtp.socketFactory.port"  (:port mail))
       (.put "mail.smtp.auth" "true"))

     (if (= (:ssl mail) true)
       (doto props
         (.put "mail.smtp.starttls.enable" "true")
         (.put "mail.smtp.socketFactory.class" 
               "javax.net.ssl.SSLSocketFactory")
         (.put "mail.smtp.socketFactory.fallback" "false")))

     (let [authenticator (proxy [javax.mail.Authenticator] [] 
                           (getPasswordAuthentication 
                             []
                             (javax.mail.PasswordAuthentication. 
                              (:user mail) (:password mail))))
           recipients (reduce #(str % "," %2) (:to mail))
           session (javax.mail.Session/getInstance props authenticator)
           msg     (javax.mail.internet.MimeMessage. session)]

       (.setFrom msg (javax.mail.internet.InternetAddress. (:user mail)))

       (.setRecipients msg 
                       (javax.mail.Message$RecipientType/TO)
                       (javax.mail.internet.InternetAddress/parse recipients))

       (.setSubject msg (:subject mail))
       (.setText msg (:text mail))
       (javax.mail.Transport/send msg)))))



(comment (defn mi-test-mail []
   (mail :user (System/getenv "SENDGRID_USERNAME")
         :password (System/getenv "SENDGRID_PASSWORD")
         :host "smtp.gmail.com"
         :port 465
         :ssl true
         :to ["juanantonioruz@gmail.com" ]
         :subject "I Have Rebooted." 
         :text "I Have Rebooted.")))


