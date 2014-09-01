(defproject chronologie "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2322"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
								 [om "0.7.1"]
								 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.3.1"]
								 [environ "1.0.0"]]

  :min-lein-version "2.0.0"

  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-environ "1.0.0"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["src"]

  :cljsbuild {
    :builds {:dev {:source-paths ["src"]
                   :compiler {
                     	:output-to "resources/public/chronologie.js"
                     	:output-dir "out/dev"
										 	:print-input-delimiter true }}
             :prod {:source-paths ["src"]
                    :compiler {
                      :output-to "resources/public/chronologie.js"
                      :output-dir "out/prod"
                      :optimizations :whitespace }}}}

  :uberjar-name "chronologie.jar"
  :main chronolgie.web
  :profiles {:production {:env {:production true}}
						 :uberjar {:aot :all}})
