(ns wordcount.core
  (:require [cljs.nodejs :as nodejs]
            [wordcount.input :as input]
            [wordcount.simple.core :as simple]
            [wordcount.all.core :as all]
            [wordcount.frequencies.core :as frequencies]
            [wordcount.parallel.core :as parallel]))


(enable-console-print!)

(defn start []
  (let [data (input/getData)
        option (input/getOption)]
    (cond
        (= option "--simple") (simple/howManyWords data)
        (= option "--all") (all/stat data)
        (= option "--frequencies") (frequencies/ofWords data)
        (= option "--parallel") (parallel/howManyWords data))))

(set! *main-cli-fn* start)
