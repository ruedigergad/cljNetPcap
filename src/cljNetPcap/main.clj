;;;
;;; Copyright (C) 2012 Ruediger Gad
;;;
;;; This file is part of cljNetPcap.
;;;
;;; cljNetPcap is free software: you can redistribute it and/or modify
;;; it under the terms of the GNU Lesser General Public License (LGPL) as
;;; published by the Free Software Foundation, either version 3 of the License,
;;; or (at your option any later version.
;;;
;;; cljNetPcap is distributed in the hope that it will be useful,
;;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;;; GNU Lesser General Public License (LGPL) for more details.
;;;
;;; You should have received a copy of the GNU Lesser General Public License (LGPL)
;;; along with cljNetPcap.  If not, see <http://www.gnu.org/licenses/>.
;;;

(ns 
  ^{:author "Ruediger Gad",
    :doc "Main class and method for launching a simple cljNetPcap based sniffer
          that prints some information about the captured packets to stdout."} 
  cljNetPcap.main
  (:use clojure.pprint
        clojure.tools.cli
        cljNetPcap.core
        cljNetPcap.native)
  (:import (java.util.concurrent Executors TimeUnit))
  (:gen-class))

(defn -main [& args]
  (let [cli-args (cli args
                      ["-i" "--interface" 
                       "Interface on which the packets are captured" 
                       :default "eth0"]
                      ["-f" "--filter" 
                       (str "Pcap filter to be used. " 
                            "Defaults to the empty String which means that all " 
                            "packets are captured.") 
                       :default ""]
                      ["-S" "--stats"
                       (str "Print stats to stderr in a regular interval."
                            "The interval is given as parameter in milliseconds."
                            "Values smaller equal 0 mean that no stats are printed."
                            "Defaults to 0.")
                       :default 0
                       :parse-fn #(Integer. %)]
                      ["-h" "--help" "Print this help." :flag true])
        arg-map (cli-args 0)
        help-string (cli-args 2)]
    (if (arg-map :help)
      (println help-string)
      (do
        (println "Starting cljNetPcap using the following options:")
        (pprint arg-map)
        (let [cljnetpcap (create-and-start-cljnetpcap
                           stdout-combined-forwarder-fn
;                           stdout-byte-array-forwarder-fn
;                           stdout-forwarder-fn 
                           (arg-map :interface) 
                           (arg-map :filter))
              stat-interval (arg-map :stats)
              executor (Executors/newSingleThreadScheduledExecutor)
              shutdown-fn (fn [] (do
                                   (println "cljNetPcap is shuting down...")
                                   (when (> stat-interval 0)
                                     (println "Stopping stat output.")
                                     (.shutdown executor))
                                   (print-stat-cljnetpcap cljnetpcap)
                                   (stop-cljnetpcap cljnetpcap)
                                   (println "Removing temporarily extracted native libs...")
                                   (remove-native-libs)))]
          (println "cljNetPcap standalone executable started.\nType \"q\" followed by <Return> to quit: ")
          (when (> stat-interval 0)
            (println "Printing stats to stderr in intervalls of" stat-interval "ms.")
            (.scheduleAtFixedRate executor #(print-stat-cljnetpcap cljnetpcap) 0 stat-interval TimeUnit/MILLISECONDS))
          ;;; Running the main from, e.g., leiningen results in stdout not being properly accessible.
          ;;; Hence, this will not work when run this way but works when run from a jar via "java -jar ...".
          (while (not= "q" (read-line))
            (println "Type \"q\" followed by <Return> to quit: "))
          (shutdown-fn)
          (println "Leaving (-main [& args] ...)."))))))

