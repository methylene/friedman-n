(ns friedman.n3)

(defrecord typed-sequence [x type])

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
  num-zeros num-ones num-twos]. This will throw an exception if x
  contains any symbols other than 0, 1 and 2."
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
          :else (throw (Exception. (str "bad input: " (first x))))))
       [n n0 n1 n2])))

(defn compressed-subsequence? [x y]
  "Subsequence relation on compressed sequences x and y. For
  uncompressed sequences a and b, (compressed-subsequence?
  (compress-seq a) (compress-seq b)) if and only if (subsequence? a b)"
  (if (seq x)
    (let [[smb-x exp-x] (first x)]
      (when-let [y' (loop [y' y]
                      (when (seq y')
                        (if (= smb-x (first (first y')))
                          y'
                          (recur (rest y')))))]
        (let [[smb-y exp-y] (first y')]
          (cond
           (> exp-y exp-x)
           (recur (rest x)
                  (cons [smb-y (- exp-y exp-x)]
                        (rest y')))
           (= exp-y exp-x)
           (recur (rest x) (rest y'))
           (< exp-y exp-x)
           (recur (cons [smb-y (- exp-x exp-y)]
                        (rest x))
                  (rest y'))))))
    true))






