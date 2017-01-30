(ns wordcount.parallel.split
    (:require [wordcount.word :as word]))

(defn split [str]
  (let [length (count str)
        middle (/ (if (odd? length) (dec length) length) 2)]
    (loop [position middle]
        (if (= position 0)
            [str]
            (let [letter (nth str position)]
                (if (word/betweenWords letter)
                    [(take position str) (drop position str)]))))))
