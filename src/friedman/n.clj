(ns friedman.n)

(defn subsequence? [x y]
  "Tests if the sequence x is a subseqence of the sequence y. Returns a
  list of indexes (of the same length as x) into y, or nil if x is not
  a subsequence of y."
  (loop [x' x y' y acc (transient [])]
    (if (seq x')
      (if (<= (count x) (count y))
        (let [i (.indexOf y' (first x'))]
          (if (not (neg? i))
            (recur (rest x') (drop (inc i) y') (conj acc i)))))
      (persistent! acc))))

(defn slice [x start end]
  "Returns a 'slice' between start index (inclusive) and end index (exclusive).
   start should be lower than end, and end should be no greater than (count x)."
  (take (- end start) (drop start x)))

(defn check-slices-at [x [a b]]
  "Tests if a slice of x, beginning at a and of length a + 1,
   is a subseq of another slice of x, beginning at b and of length b + 1.
   The caller must ensure that (< a b), and b is so that (<= (* 2 (inc b)) (count x))."
  (let [y (slice x a (* 2 (inc a)))
        z (slice x b (* 2 (inc b)))]
    (if (subsequence? y z)
      {:seq x
       :start-indexes [a b]})))

(defn ordered-pairs-below
  ([n]
     (loop [acc (transient []) i 1 j 2]
       (if (<= j (/ n 2))
         (if (= 1 (- j i))
           (recur (conj! acc [i j]) 1 (inc j))
           (recur (conj! acc [i j]) (inc i) j))
         (persistent! acc))))
  ([i j]))



(defn is-not-* [y]
  "Determines whether seq y has property *. If y fails to have property
   *, the start indexes of the offending subsequences are returned"
  (loop [ordered-pairs (ordered-pairs-below (count y))]
    (when (seq ordered-pairs)
      (let [])
      (if-let [slices-info (check-slices-at (first ivs) y)]
        {:offending-start-indexes (first ivs)
         :bad-things bad-things}
        (recur (rest ordered-pairs))))))

