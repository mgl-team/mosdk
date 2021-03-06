(ns app.state
  (:require
   [rum.core :as rum]
   [app.util :as util]))

(def ^:private state
  (atom
   {:ime/active? true
    :ime/candidate []
    :ime/candidate-page 0
    :ime/input ""
    :ime/candidate-left "0px"
    :ime/candidate-top "0px"
    
    :editor nil
    :test true}))

(defn sub
  [ks]
  (if (coll? ks)
    (util/react (rum/cursor-in state ks))
    (util/react (rum/cursor state ks))))

(defn set-state!
  [path value]
  (if (vector? path)
    (swap! state assoc-in path value)
    (swap! state assoc path value)))

(defn update-state!
  [path f]
  (if (vector? path)
    (swap! state update-in path f)
    (swap! state update path f)))

(defn setv [o k v]
  (if (list? k)
    (swap! o assoc-in k v)
    (swap! o assoc k v)))

(defn getv [o k]
  (if (list? k)
    (get-in @o k)
    (get @o k)))
