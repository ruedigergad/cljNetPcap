;(defproject cljNetPcap "1.6.2"
(defproject cljNetPcap "1.7.0-SNAPSHOT"
  :description "cljNetPcap is a wrapper/adapter/facade (No matter how you wanna call it.) 
                around jNetPcap that enables and eases packet capturing with Clojure."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.1"]
                 [cljAcmeUtils "1.6.0"]
                 [jnetpcap "1.4.r1380-2"]]
  :main cljNetPcap.main
  :java-source-path "src"
  :omit-source true)
