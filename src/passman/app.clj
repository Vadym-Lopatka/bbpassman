(ns passman.app
  (:require [clojure.tools.cli :refer [parse-opts]]
            [passman.db :as db]
            [passman.password :as pwd]
            [passman.stash :as stash]
            [passman.clipboard :as clipboard]
            [table.core :as t]))

(def cli-options
   [["-l" "--length Length" "Password length"
     :default 12
     :parse-fn #(Integer/parseInt %)
     :validate [#(< 0 % 100) "Must be a number between 0 and 100"]]
    ["-g" "--generate" "Generate new password"]
    [nil "--list"]])

(defn master-key []
  (println "Enter your master key: ")
  (String. (.readPassword (System/console))))

(defn -main [& args]
  (let [parsed-options (parse-opts args cli-options)
        url (first (:arguments parsed-options))
        username (second (:arguments parsed-options))
        options (:options parsed-options)]
    

    
    (cond 
      (:generate options) (do
                            (stash/init-stash (master-key)) 
                            (let [password (pwd/generate-password (:length options))]
                              (db/insert-password url username)
                              (stash/add-password url username password)
                              (println "Password copied to the clipboard")
                              (clipboard/copy password)))
      (and url username) (do
                           (stash/init-stash (master-key))
                           (let [pwd (stash/find-pwd url username)] 
                             (clipboard/copy pwd)
                             (println "Password copied to the clipboard")))
      
      (:list options) (t/table (db/get-passwords)))))

;; BUGS
;; - works strange on empty db

(comment
  (-main)
  )