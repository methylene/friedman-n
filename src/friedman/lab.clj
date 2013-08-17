(ns friedman.lab)

(defn next-symbol [symbols smb]
  "Returns the symbol after smb in symbols. If smb is the last symbol
  in symbols, returns the first symbol in symbols. It is expected that
  symbols contains smb"
  (let [i (inc (.indexOf symbols smb))]
    (if (= i (count symbols))
      (first symbols)
      (get symbols i))))

(defn change-sequence-at [s n new-val]
  "Returns a sequence x where x[n] = new-val, and x[i] = s[i] where s != n"
  (if (zero? n)
    (cons new-val (rest s))
    (concat (take n s) (cons new-val (drop (inc n) s)))))

(defn next-string
  "An implementation of (bounded) increment with carry. Increment the
  first letter in the string (sequence) s. If the first letter was
  already the last letter of the alphabet symbols, set it to the first
  letter and proceed in a recursive fashion with the next letter,
  until a letter different from the last letter of the alphabet is
  found, or the string is exhausted because all its letters were
  already the last element in the alphabet. In the latter case, return
  nil."
  ([symbols s]
     (next-string symbols s 0))
  ([symbols s n]
     (if  (< n (count s))
       (let [s-at-n' (next-symbol symbols (nth s n))]
         (if (= (first symbols) s-at-n')
           (recur symbols (change-sequence-at s n s-at-n') (inc n))
           (change-sequence-at s n s-at-n'))))))

(defn- all-strings [symbols s]
  "Internal implementation of all-strings-of-length"
  (if-let [s' (next-string symbols s)]
    (cons s (lazy-seq (all-strings symbols s')))
    (list s)))

(defn all-strings-of-length [symbols n]
  "Returns a lazy finite sequence of all sequences of length n, with
  symbols from the finite alphabet symbols. For example, if symbols =
  [0 1] and n = 10, returns all 1024 binary sequences of length 10."
  (all-strings symbols (take n (repeat (first symbols)))))

