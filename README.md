tangram
=======

clojure and clojurescript webapp to manage a coworking space

#Requirements

* *lein-ring* https://github.com/weavejester/lein-ring
How to startup ring server from $ run lein
* *lein-cljsbuild* https://github.com/emezeske/lein-cljsbuild
Tool to easy compile (better development) clojure in javascript

# Developing process:

It's necesary to have a file to connect cljs/modern-cljs/connect.cljs

###OPEN SHELL:

$ lein ring server-headless ---> will startup listening in port 3000 

###OPEN SHELL:

$ lein trampoline cljsbuild auto

###OPEN OTHER SHELL

$ lein trampoline cljsbuild repl-listen

###OPEN WEB PAGE: 

http://localhost:5000/pub/simple.html


###Type in the cljs-shell:

(js/alert "whatever phrase")



### if there is errors
* lein clean
* lein trampoline cljsbuild clean
* lein  trampoline cljsbuild repl-listen



#clojurescript refresh changes

http://stackoverflow.com/questions/14694222/clojurescript-namespace-refresh

The browser repl-listen depends on browser refresh, so if we changes the cljs source files and we are compiling with "lein trampoline cljsbuild auto" then only rest to refresh browser to let brepl access to the changes





.....
If you want to connect to REST services you have to start mongo db and ring server
FILE to manually run Ring Server: clj/modern-cljs/core.clj




Online Version

http://serene-shore-5490.herokuapp.com/

#TODO
first commit 

#Bassed in 

https://github.com/magomimmo/modern-cljs

http://thecomputersarewinning.com/post/clojure-heroku-noir-mongo/

