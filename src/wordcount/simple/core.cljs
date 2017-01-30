(ns wordcount.simple.core
    (:require [wordcount.word :as word]))


(defn howManyWords [data]
    (loop [word false
           chars data
           res 0]
        (let [head (first chars)]
          (if (nil? head)
            (println res)
            (let [ok (word/inWord head)]
              (recur
                ok
                (rest chars)
                (if (and (false? word) (true? ok))
                  (inc res)
                  res)))))))
