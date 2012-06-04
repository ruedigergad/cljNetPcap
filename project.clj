;(defproject cljNetPcap "1.4.2"
(defproject cljNetPcap "1.5.0-SNAPSHOT"
  :description "cljNetPcap is a wrapper/adapter/facade (No matter how you wanna call it.) 
                around jNetPcap that enables and eases packet capturing with Clojure."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.1"]
                 [cljAcmeUtils "1.5.0"]
                 [jnetpcap "1.4.r1300-2"]]
  :main cljNetPcap.main
  :java-source-path "src"
  :omit-source true)