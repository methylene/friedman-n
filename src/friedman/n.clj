(ns friedman.n)

(defn subseq? [y z]
  (and (<= (count y) (count z))
       (every? identity (map = y z))))

(defn slice [y start end]
  (take (- end start) (drop start y)))

(defn bad-interval? [interval y]
  (if (or
       (nil? (first interval))
       (nil? (second interval)))
    (str "ouch: " interval)
    (let [i (first interval)
          j (second interval)
          iseq (slice y (dec i) (* 2 i))
          jseq (slice y (dec j) (* 2 j))]
      (if (subseq? iseq jseq)
        [iseq jseq]))))

(defn intervals [n]
  (loop [acc (transient []) i 1 j 2]
    (if (<= j (/ n 2))
      (if (= 1 (- j i))
        (recur (conj! acc [i j]) 1 (inc j))
        (recur (conj! acc [i j]) (inc i) j))
      (persistent! acc))))

(defn is-not-* [y]
  "Determines whether seq y has property *.
   If y fails to have property, the offending subsequences
   are returned along with their 1-based start indexes i and j.
   Their 1-based end indexes (inclusive) are 2i and 2j, respectively."
  (loop [ivs (intervals (count y))]
    (when (seq ivs)
      (if-let [bad-seqs (bad-interval? (first ivs) y)]
        {:offending-start-indexes (first ivs)
         :offending-subseqs bad-seqs}
        (recur (rest ivs))))))

