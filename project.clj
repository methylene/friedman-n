(defproject friedman.n "0.1.0-SNAPSHOT"
  :license {:name "BSD"
            :url "http://opensource.org/licenses/BSD-2-Clause"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]]
  :repl-options {:port 9099}
  :jvm-opts ["-Xmx768m"]
  :main friedman.core)
