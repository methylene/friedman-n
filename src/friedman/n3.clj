(ns friedman.n3)

;; Specialized functions for the case of 3 symbols {0, 1, 2}

(defn compress-seq
  "Compresses the sequence x to a list of pairs [symbol exponent]. For
  example, '(0 0 1 1 1 2) becomes '([0 2] [1 3] [2 1])"
  ([x] (compress-seq x '()))
  ([x acc]
     (if (seq x)
       (if (= (first x) (first (first acc)))
         (recur (rest x)
                (cons [(first x) (inc (second (first acc)))] (rest acc)))
         (recur (rest x) (cons [(first x) 1] acc)))
       (reverse acc))))

(defn sequence-type
  "Returns the type of the compressed sequence x: A 4-tuple [length
  num-zeros num-ones num-twos]"
  ([x]
     (sequence-type x 0 0 0 0))
  ([x n n0 n1 n2]
     (if (seq x)
       (let [rx (rest x)
             caar (first (first x))
             cadar (second (first x))]
         (cond
          (= 0 caar) (recur rx (inc n) (+ n0 cadar) n1 n2)
          (= 1 caar) (recur rx (inc n) n0 (+ n1 cadar) n2)
          (= 2 caar) (recur rx (inc n) n0 n1 (+ n2 cadar))
          :else (first x)))
       [n n0 n1 n2])))





