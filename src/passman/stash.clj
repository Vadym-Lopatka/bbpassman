(ns passman.stash)

(def ^{:doc "path to stash file"} path "bbpassman.stash")

(babashka.pods/load-pod 'rorokimdim/stash "0.3.3")

(require '[pod.rorokimdim.stash :as stash])

(defn init-stash
  "Loads stash file from given path. Returns true on success, false on error."
  ([password] (init-stash password true))
  ([password create-stash-if-missing] 
   (stash/init {:encryption-key password
                :stash-path path
                :create-stash-if-missing create-stash-if-missing})))

(defn stash-add [parent-id k v]
  (stash/add parent-id k v))

(defn add-password [url username password]
  (stash-add 0 (str url username) password))

(defn stash-nodes 
  "Get all nodes stored in stash"
  ([] (stash-nodes 0))
  ([parent-id] (stash/nodes parent-id)))

(defn find-pwd [url username]
  (let [nodes (stash-nodes)
        key (str url username)
        found-node (first (filter (fn [n] (= (:key n) key)) nodes))]
    (:value found-node)))

(comment
  (init-stash "password")

  (add-password "fb.com" "vl@gmail.com" "the-pwd")

  (stash-nodes)

  (find-pwd "fb.com" "vl@gmail.com")
  )