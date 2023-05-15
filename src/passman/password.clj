(ns passman.password
  (:require [clojure.string :as str]))

(def available-chars
  (map char (range 33 123)))

(defn generate-password [length]
  (str/join (repeatedly length #(rand-nth available-chars))))


(comment
  (generate-password 20)
  )