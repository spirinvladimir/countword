(ns wordcount.word)

(def O9 ["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"])
(def az ["q" "w" "e" "r" "t" "y" "u" "i" "o" "p" "a" "s" "d" "f" "g" "h" "j" "k" "l" "z" "x" "c" "v" "b" "n" "m"])
(def AZ ["Q" "W" "E" "R" "T" "Y" "U" "I" "O" "P" "A" "S" "D" "F" "G" "H" "J" "K" "L" "Z" "X" "C" "V" "B" "N" "M"])
(def dict (concat O9 az AZ))
(def hashDict (reduce (fn [res key] (assoc res key true)) {} dict))

(defn inWord [letter]
  (if (true? (get hashDict letter))
    true
    false))

(defn betweenWords [letter]
  (not (inWord letter)))
