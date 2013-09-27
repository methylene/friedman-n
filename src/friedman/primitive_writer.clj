(ns friedman.primitive-writer)

(defprotocol PrimitiveWriter
  (write-primitive [writer p]))
