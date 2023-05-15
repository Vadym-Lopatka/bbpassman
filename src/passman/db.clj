(ns passman.db
  (:require [babashka.pods :as pods]
            [honey.sql :as sql]
            [honey.sql.helpers :as h]
            [babashka.fs :as fs]))

(pods/load-pod 'org.babashka/go-sqlite3 "0.1.0")
(require '[pod.babashka.go-sqlite3 :as sqlite])

(def db-name "bbpassman.db")

(defn create-db! []
  (when (not (fs/exists? db-name))
    (sqlite/execute! db-name
                     (-> (h/create-table :passwords)
                         (h/with-columns [[:url :text]
                                          [:username :text]
                                          [[:unique nil :url :username]]])
                         (sql/format)))))

(defn insert-password [url username]
  (sqlite/execute! db-name
                   (-> (h/insert-into :passwords)
                       (h/columns :url :username)
                       (h/values [[url username]])
                       (sql/format))))


(defn get-passwords []
  (sqlite/query db-name
                (-> (h/select :url :username)
                    (h/from :passwords)
                    (sql/format))))


(comment 
  (create-db!)

  (insert-password "twitter.com" "vl.test@gmail.com")

  (get-passwords))




