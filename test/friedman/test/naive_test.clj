(ns friedman.test.naive-test
  (:require [clojure.test :refer [deftest testing is]]
            [friedman.n3 :refer [uncompress]]
            [friedman.n :refer [is-not-*]]))

(def special-sequence
  (uncompress
   '([0 1] [1 2] [0 1] [2 1] [0 7] [2 2] [0 1] [2 1] [0 1] [2 8]
       [0 1] [2 5] [0 1] [2 20] [0 2] [2 53] [0 1] [2 108])))

(deftest some-test 
  (testing "Check length" 
    (is (= 216 (count special-sequence))))
  (testing "Check *" 
    (is (not (is-not-* special-sequence)))))
