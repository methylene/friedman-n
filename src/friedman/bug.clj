(ns friedman.bug)

(defprotocol Input
  (write [y x]))

(deftype StringBuilderInput [^StringBuilder sb]
  Input
  (write [y x] (.append sb (str x))))

(derive clojure.lang.IPersistentVector ::collection)
(derive clojure.lang.ISeq ::collection)
(derive clojure.lang.IPersistentMap ::map)

(defmulti write (fn [x y] (class y)))

(defmethod write ::collection [x y] (str "foobar " (count y)))
(defmethod write ::map [x y] (str  "izmap " (count y)))

