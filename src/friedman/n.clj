(ns friedman.n)

(defn subsequence? [x y]
  "Returns true if the sequence x is a subsequence of the sequence y,
  otherwise nil."
  (if (seq x)
    (if (<= (count x) (count y))
      (let [i (.indexOf y (first x))]
        (if (not (neg? i))
          (recur (rest x)
                 (drop (inc i) y)))))
    true))

(defn slice [x start end]
  "Returns a 'slice' of the sequence x between start index (inclusive)
  and end index (exclusive). The start index should not be greater
  than the end index, and the end index should not be greater than
  the length of x. The length of the returned sequence will be equal
  to the difference between end and start."
  (take (- end start) (drop start x)))

(defn check-slices-at [x [a b]]
  "Returns [a b] if a slice of x, beginning at index a and of length a + 1,
  is a subseq of another slice of x, beginning at index b and of length b + 1.
  Otherwise, returns nil. Please be sure that a and b are not negative or
  too large, i.e. (<= (* 2 (inc a)) (count x)) and (<= (* 2 (inc b)) (count x))"
  (let [y (slice x a (* 2 (inc a)))
        z (slice x b (* 2 (inc b)))]
    (if (subsequence? y z) [a b])))

(defn << [p q]
  "Defines an ordering on pairs. Returns true if each element of
   p is strictly lower than the corresponding element of q,
   respectively. Otherwise, returns false."
  (and (< (first p) (first q))
       (< (second p) (second q))))

(defn next-pair [[i j]]
  "Returns the 'next' stricly sorted pair: Increment i if that results
  in another strictly sorted pair. Otherwise, increment j and set i to
  0. This makes more sense if the input is a strictly sorted pair."
  (if (>= (inc i) j)
    [0 (inc j)]
    [(inc i) j]))

(defn pairs-below
  "Returns a lazy sequence of all strictly sorted pairs of non-negative
  ints [i j] for which (<< [i j] limit) is true. For example,
  (pairs-below [3 3]) returns the following list: '([0 1] [0 2] [1 2])"
  ([limit]
     (pairs-below limit [0 1]))
  ([limit p]
     (when (<< p limit)
       (cons p (lazy-seq (pairs-below limit (next-pair p)))))))

(defn is-not-* [x]
  "Determines whether sequence x has property *. If x fails to have property *,
  the start indexes of the offending slices are returned"
  (let [n (int (/ (count x) 2))]
    (loop [pairs (pairs-below [n n])]
      (when (seq pairs)
        (if-let [slices-info (check-slices-at x (first pairs))]
          slices-info
          (recur (rest pairs)))))))

