;(defproject cljNetPcap "1.6.3"
(defproject cljNetPcap "1.7.0-SNAPSHOT"
  :description "cljNetPcap is a wrapper/adapter/facade (No matter how you wanna call it.) 
                around jNetPcap that enables and eases packet capturing with Clojure."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.2"]
                 [cljAcmeUtils "1.7.0"]
                 [jnetpcap "1.4.r1390-1a"]
                 [ruiyun/tools.timer "1.0.0-SNAPSHOT"]]
  :main cljNetPcap.main
  :java-source-path "src"
  :omit-source true)
