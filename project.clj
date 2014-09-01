(defproject chronologie "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.0"]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]]

  :min-lein-version "2.0.0"

  :plugins [[lein-cljsbuild "1.0.2"]
            [environ/environ.lein "0.2.1"]]

  :hooks [environ.leiningen.hooks]

  :source-paths ["src"]

  :cljsbuild {
    :builds {:dev {:id "chronologie"
                   :source-paths ["src"]
                   :compiler {
                     :output-to "resources/public/chronologie.js"
                     :output-dir "out"
                     :optimizations :whitespace }}
             :prod {:id "chronologie"
                    :source-paths ["src"]
                    :compiler {
                      :output-to "resources/public/chronologie.js"
                      :output-dir "out"
                      :optimizations :whitespace }}}}

  :uberjar-name "chronologie.jar"
  :main chronolgie.web
  :profiles {:production {:env {:production true}}})
