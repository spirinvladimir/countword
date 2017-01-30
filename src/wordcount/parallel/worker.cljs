(ns wordcount.parallel.worker)

(defn start [cluster]
  (let [worker (.-worker cluster)
        process (.-process worker)
        O9 ["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"]
        az ["q" "w" "e" "r" "t" "y" "u" "i" "o" "p" "a" "s" "d" "f" "g" "h" "j" "k" "l" "z" "x" "c" "v" "b" "n" "m"]
        AZ ["Q" "W" "E" "R" "T" "Y" "U" "I" "O" "P" "A" "S" "D" "F" "G" "H" "J" "K" "L" "Z" "X" "C" "V" "B" "N" "M"]
        dict (concat O9 az AZ)
        hashDict (reduce (fn [res key] (assoc res key true)) {} dict)
        inWord (fn [letter] (if (true? (get hashDict letter)) true false))
        onMessage (fn [data]
                    (let [howManyWords (fn [data]
                                         (loop [word false
                                                chars data
                                                res 0]
                                           (let [head (first chars)]
                                             (if (nil? head)
                                               res
                                               (let [ok (inWord head)]
                                                 (recur
                                                   ok
                                                   (rest chars)
                                                   (if (and (false? word) (true? ok))
                                                     (inc res)
                                                     res)))))))]
                      (.send process (howManyWords (js->clj data)))
                      (.kill worker)))]
    (.on process "message" onMessage)))
