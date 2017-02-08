(ns wordcount.parallel.split
  (:require [wordcount.word :refer [betweenWords]]))


(defn split [data cpus parts length]
  (if (= cpus 1)
    (conj parts data)
    (if (< length cpus)
      (conj parts data)
      (let [startPosition (int (/ length cpus))]
        (loop [position startPosition]
          (if (= position 0)
            (split
              (drop (dec startPosition) data)
              cpus
              parts
              (- length (dec startPosition)))
            (let [char (nth data position)]
              (if (betweenWords char)
                (split
                  (drop position data)
                  (dec cpus)
                  (conj parts (take position data))
                  (- length position))
                (recur (dec position))))))))))


(defn splitByCPU [data]
  (let [os (js/require "os")
        cpus (-> os .cpus .-length)
        length (count data)
        parts []]
    (split data cpus parts length)))
