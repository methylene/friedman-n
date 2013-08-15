(ns friedman.core
  (:require [friedman.n :refer [is-not-*]]
            [clojure.tools.logging :refer [infof]]
            [clojure.string :refer [join]])
  (:gen-class))

(defn -main
  [& args]
  (let [y '(1 2 2 2 1 1 1 1 1 1 1)] ; try with one more 1 at the end
    (if-let [fail-info (is-not-* y)]
      (infof (str "Sequence y = %s doesn't have the star property:%n"
                  "y [%d], ... , y [%d] = '(%s)%n"
                  "y [%d], ... , y [%d] = '(%s)")
             y
             (first (:offending-start-indexes fail-info))
             (* 2 (first (:offending-start-indexes fail-info)))
             (join " " (first (:offending-subseqs fail-info)))
             (second (:offending-start-indexes fail-info))
             (* 2 (second (:offending-start-indexes fail-info)))
             (join " " (second (:offending-subseqs fail-info))))
      (infof "Sequence y = %s has the star property." y))))
