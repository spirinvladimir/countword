(ns wordcount.simple.core
    (:require [wordcount.word :refer [inWord]]))


(defn simple [data]
    (loop [word false
           chars data
           res 0]
        (let [head (first chars)]
          (if (nil? head)
            (println res)
            (let [ok (inWord head)]
              (recur
                ok
                (rest chars)
                (if (and (false? word) (true? ok))
                  (inc res)
                  res)))))))
