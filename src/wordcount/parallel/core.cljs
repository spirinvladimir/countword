(ns wordcount.parallel.core
    (:require [cljs.nodejs :as nodejs]
              [wordcount.parallel.split :as split]
              [wordcount.parallel.worker :as worker]))


(defn howManyWords [data]
    (let [cluster (js/require "cluster")]
      (if (.-isMaster cluster)
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
              (split/split data))
            "ready"
            (fn [& args]
                (let [state (last args)]
                    (when (= (:notFinish state) 0)
                        (println (:wordcount state))))))
        (worker/start cluster))))
