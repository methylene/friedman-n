(ns friedman.f)

(defn nmi
  ([a b] (nmi a b 2))
  ([a b n]
     (lazy-seq
      (cons a (nmi b (inc (* 2 a)))))))


