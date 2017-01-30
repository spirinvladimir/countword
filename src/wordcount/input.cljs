(ns wordcount.input
    (:require [cljs.nodejs :as nodejs]
              [clojure.string :as string]))

(def argv (drop 2 (js->clj (.-argv js/process))))

(defn getData []
  (let [fileName (last argv)
        fs (js/require "fs")
        text (.readFileSync fs fileName "utf8")]
    (string/split text #"")))

(defn getOption []
  (if (= 2 (count argv)) (first argv) "--simple"))
