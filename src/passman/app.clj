(ns passman.app
  (:require [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
   [["-l" "--list"]])

(defn -main [& args]
  (let [parsed-options (parse-opts args cli-options)
        options (:options parsed-options)]
    (cond
      (:list options) (println "The list of passwords"))))
