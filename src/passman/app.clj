(ns passman.app
  (:require [clojure.tools.cli :refer [parse-opts]]
            [passman.db :as db]
            [passman.password :as pwd]
            [table.core :as t]))

(def cli-options
   [["-l" "--length Length" "Password length"
     :default 12
     :parse-fn #(Integer/parseInt %)
     :validate [#(< 0 % 100) "Must be a number between 0 and 100"]]
    ["-g" "--generate" "Generate new password"]
    [nil "--list"]])

(defn -main [& args]
  (let [parsed-options (parse-opts args cli-options)
        url (first (:arguments parsed-options))
        username (second (:arguments parsed-options))
        options (:options parsed-options)]
    
    (println (str "url: " url " username " username))
    (println options)
    (cond
      (:list options) (t/table (db/get-passwords))
      (:generate options) (let [password (pwd/generate-password (:length options))]
                            (println password)))))

(comment
  (-main)
  )

