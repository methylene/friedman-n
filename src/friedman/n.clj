(ns friedman.n)

(defn subsequence? [x y]
  "Tests if the sequence x is a subseqence of the sequence y. Returns
  true if x is a subsequence of y, otherwise nil."
  (loop [x' x y' y]
    (if (seq x')
      (if (<= (count x') (count y'))
        (let [i (.indexOf y' (first x'))]
          (if (not (neg? i))
              (recur (rest x')
                     (drop (inc i) y')))))
      true)))

(defn slice [x start end]
  "Returns a 'slice' between start index (inclusive) and end index (exclusive).
   start should be lower than end, and end should be no greater than (count x)."
  (take (- end start) (drop start x)))

(defn check-slices-at [x [a b]]
  "Tests if a slice of x, beginning at a and of length a + 1, is a
   subseq of another slice of x, beginning at b and of length b + 1.
   The caller must ensure that the pair [a b] is strictly sorted, and
   b is not too big, i.e. (<= (* 2 (inc b)) (count x)). Returns the
   indexes of the lower slice in the upper slice, or nil if the lower
   slice is not a subsequence of the higher slice."
  (let [y (slice x a (* 2 (inc a)))
        z (slice x b (* 2 (inc b)))]
    (if (subsequence? y z)
      {:slice-start-indexes [a b]})))

(defn pair-lt? [p q]
  (and (< (first p) (first q))
       (< (second p) (second q))))

(defn inc-pair [[i j]]
  "Returns the 'next' pair, according to lexicographic ordering (j is
  more significant). The caller must ensure that i < j, i.e. the pair
  is strictly sorted."
  (if (= 1 (- j i))
    [0 (inc j)]
    [(inc i) j]))

(defn pairs-below
  ([limit]
     (pairs-below limit [0 1]))
  ([limit p]
     (if (pair-lt? p limit)
       (cons p (lazy-seq (pairs-below limit (inc-pair p)))))))

(defn is-not-* [y]
  "Determines whether seq y has property *. If y fails to have property
   *, the start indexes of the offending subsequences are returned"
  (let [n (int (/ (count y) 2))]
    (loop [pairs (pairs-below [n n])]
      (when (seq pairs)
        (if-let [slices-info (check-slices-at y (first pairs))]
          slices-info
          (recur (rest pairs)))))))

