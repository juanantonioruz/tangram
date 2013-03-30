(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"

  ;; clojure source code path
  :source-paths ["src/clj"]

  :dependencies [

                 [hiccups "0.2.0"]
                 [org.clojure/clojure "1.5.1"]
                 [jayq "2.3.0"]
                 [compojure "1.1.5"]
                 [domina "1.0.2-SNAPSHOT"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [congomongo "0.3.2"]
                 [hiccups "0.2.0"]
                 [shoreleave/shoreleave-remote-ring "0.3.0"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [javax.mail/mail "1.4.3"]
                 ]
  

  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-ring "0.8.3"][lein-swank "1.4.5"]]

  ;; ring tasks configuration
  :ring {:handler elavio.remotes/app}

  ;; cljsbuild tasks configuration

  :cljsbuild {:builds
              [{;; clojurescript source code path
                :source-paths ["src/cljs"]

                ;; Google Closure Compiler options
                :compiler {;; the name of emitted JS script file
                           :output-to "resources/public/js/modern.js"

                           ;; minimum optimization
                           :optimizations :whitespace
                           ;; prettyfying emitted JS
                           :pretty-print true}}]}
  :main modern-cljs.core
  )
