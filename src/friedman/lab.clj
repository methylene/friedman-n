(ns friedman.lab)

(defn next-symbol [symbols smb]
  (let [i (inc (.indexOf symbols smb))]
    (if (= i (count symbols))
      (first symbols)
      (get symbols i))))

(defn change-sequence-at [s n new-val]
  (if (zero? n)
    (cons new-val (rest s))
    (concat (take n s) (cons new-val (drop (inc n) s)))))

(defn next-string
  ([symbols s]
     (next-string symbols s 0))
  ([symbols s n]
     (if  (< n (count s))
       (let [s-at-n' (next-symbol symbols (nth s n))]
         (if (= (first symbols) s-at-n')
           (recur symbols (change-sequence-at s n s-at-n') (inc n))
           (change-sequence-at s n s-at-n'))))))

(defn all-strings [symbols s]
  (if-let [s' (next-string symbols s)]
    (cons s (lazy-seq (all-strings symbols s')))
    (list s)))

(defn all-strings-of-length [symbols n]
  (all-strings symbols (take n (repeat (first symbols)))))

