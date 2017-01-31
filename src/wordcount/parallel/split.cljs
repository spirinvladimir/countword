(ns wordcount.parallel.split
    (:require [wordcount.word :refer [betweenWords]]))

(defn split [data cpus bound parts length]
  (if (= cpus 1)
    (conj parts data)
    (let [diapason (- length bound)]
      (if (< diapason cpus)
        (conj parts data)
        (let [startPosition (+ bound (int (/ diapason cpus)))]
          (loop [position startPosition]
            (if (= position bound)
              (split data cpus startPosition parts length)
              (let [char (nth data position)]
                (if (betweenWords char)
                  (split
                    (drop position data)
                    (dec cpus)
                    0
                    (conj parts (take position data))
                    (- length position))
                  (recur (dec position)))))))))))

(defn splitByCPU [data]
  (let [os (js/require "os")
        cpus (-> os .cpus .-length)
        leftBound 0
        length (count data)
        parts []]
    (split data cpus leftBound parts length)))
