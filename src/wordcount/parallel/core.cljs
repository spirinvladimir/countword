(ns wordcount.parallel.core
    (:require [wordcount.parallel.split :refer [splitByCPU]]
              [wordcount.parallel.worker :refer [worker]]
              [wordcount.input :refer [getData]]))


(defn parallel []
    (let [cluster (js/require "cluster")]
      (if (.-isMaster cluster)
        (let [data (getData)]
          (add-watch
            (reduce
              (fn [manager data]
                (let [worker (.fork cluster)]
                  (swap! manager update-in [:notFinish] inc)
                  (.on worker "online" (fn []
                                         (.send worker (clj->js data))))
                  (.on worker "message" (fn [result]
                                          (swap! manager update-in [:wordcount] (fn [n] (+ n result)))
                                          (swap! manager update-in [:notFinish] dec)))
                  manager))
              (atom {:wordcount 0
                     :notFinish 0})
              (splitByCPU data))
            "Have a nice day"
            (fn [& args]
              (let [state (last args)]
                (when (= (:notFinish state) 0)
                  (println (:wordcount state)))))))
        (worker cluster))))
