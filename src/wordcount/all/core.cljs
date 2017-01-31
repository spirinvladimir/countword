(ns wordcount.all.core
    (:require [wordcount.word :refer [inWord]]))


(defn all [text]
    (loop [word false
           data text
           words 0
           lines 0
           chars 0]
        (let [head (first data)]
          (if (nil? head)
            (println (str "line:" lines ", words:" words ", chars:" chars))
            (let [ok (inWord head)]
              (recur
                ok
                (rest data)
                (if (and (false? word) (true? ok))
                  (inc words)
                  words)
                (if (= "\n" head)
                  (inc lines)
                  lines)
                (if (true? ok)
                  (inc chars)
                  chars)))))))
