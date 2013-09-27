(ns friedman.bug
  (:require [friedman.primitive-writer :as pw :refer [write-primitive]])
  (:import [friedman.primitive_writer PrimitiveWriter]))

(extend-protocol pw/PrimitiveWriter
  StringBuilder
  (write-primitive [writer p] (.append writer (str p))))

(derive clojure.lang.IPersistentVector ::coll)
(derive clojure.lang.ISeq ::coll)
(derive clojure.lang.IPersistentMap ::m)

(defmulti write (fn [^PrimitiveWriter writer x] (class x)))

(defmethod write ::coll [writer coll]
  (write-primitive writer \c)
  (doseq [item coll]
    (write-primitive writer item)))

(defmethod write ::m [writer m]
  (write-primitive writer \m)
  (doseq [[k v] m]
    (write-primitive writer k)
    (write-primitive writer v)))

