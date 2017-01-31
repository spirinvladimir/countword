(ns wordcount.frequencies.core
    (:require [wordcount.word :refer [inWord]]))


(defn frequencies [data]
  (let [updateWord (fn [now was char word]
                      (if (true? now)
                        (conj word char)
                        (if (true? was)
                          (list)
                          word)))
        reverseKey (fn [revesed char] (str char revesed))
        updateWords (fn [now was word words]
                      (if (and (true? was) (false? now))
                        (let [
                              key (reduce reverseKey word)
                              times (get words key)]
                          (if (nil? times)
                            (assoc words key 1)
                            (assoc words key (inc times))))
                        words))
        sortIt (fn [words]
                 (into
                   (sorted-map-by
                     (fn [key1 key2]
                       (compare [(get words key2) key2]
                                [(get words key1) key1])))
                   words))
        formatIt (fn [words]
                   (let [wordKeys (keys words)
                         head (first wordKeys)
                         tail (rest wordKeys)]
                     (when (not (nil? head))
                       (reduce
                         (fn [line key]
                           (str line " [" key " " (get words key) "]"))
                         (str "[" head " " (get words head) "]")
                         tail))))
        outputIt (fn [words]
                   (println
                     (formatIt
                       (sortIt words))))]
    (loop [was false
           chars data
           word (list)
           words (hash-map)]
      (let [char (first chars)]
        (if (nil? char)
          (outputIt (updateWords false was word words))
          (let [now (inWord char)]
            (recur
              now
              (rest chars)
              (updateWord now was char word)
              (updateWords now was word words))))))))
