(ns friedman.core
  (:require [friedman.n :refer [is-not-* slice]]
            [friedman.lab :refer [all-strings-of-length]]
            [clojure.tools.logging :refer [infof]]
            [clojure.string :refer [join]])
  (:gen-class))

(defn -main
  [& args]
  (doseq [x (all-strings-of-length [0 1] 11)]
    (if (not (is-not-* x))
      (printf "Sequence x = (%s) has property *.%n" (join " " x))))
  )

(defn print-sequence-info [x]
  (if-let [fail-info (is-not-* x)]
      (let [i0 (first fail-info)
            i1 (* 2 (inc (first fail-info)))
            j0 (second fail-info)
            j1 (* 2 (inc (second fail-info)))
            low-seq (slice x i0 i1)
            high-seq (slice x j0 j1)]
        (infof (str "Sequence x = %s doesn't have property *:%n"
                    "y = x[%d], ..., x[%d] = '(%s)%n"
                    "is a subsequence of%n"
                    "z = x[%d], ..., x[%d] = '(%s)%n"
                    "(0-based indexes)")
               x i0 (dec i1) (join " " low-seq) j0 (dec j1) (join " " high-seq)))
      (infof "Sequence x = %s has property *." x)))
