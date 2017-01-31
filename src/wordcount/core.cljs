(ns wordcount.core
  (:require [wordcount.input :refer [getData getOption]]
            [wordcount.simple.core :refer [simple]]
            [wordcount.all.core :refer [all]]
            [wordcount.frequencies.core :refer [frequencies]]
            [wordcount.parallel.core :refer [parallel]]))


(enable-console-print!)

(defn start []
  (let [option (getOption)]
    (cond
        (= option "--simple") (simple (getData))
        (= option "--all") (all (getData))
        (= option "--frequencies") (frequencies (getData))
        (= option "--parallel") (parallel))))

(set! *main-cli-fn* start)
