(defproject wordcount "1.0.5"

  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.456"]]

  :plugins [[lein-cljsbuild "1.1.5"]]

  :source-paths ["src"]

  :clean-targets ["index.js"
                  "target/out"]

  :cljsbuild {
              :builds [{:source-paths ["src"]
                        :compiler {
                                   :output-to "index.js"
                                   :output-dir "target/index"
                                   :target :nodejs
                                   :optimizations :simple}}]})
