(ns app.common.scroll
  (:require
   [app.common.browser :as browser]))


(defn get-client-width [elem]
  (if elem (.-clientWidth elem) 0))

(defn get-client-height [elem]
  (if elem (.-clientWidth elem) 0))

(defn round-for-scale [value]
  (if (<= (- value (js/Math.floor value)) 0.5)
    (js/Math.floor value)
    (js/Math.round value)))

(comment 
  (get-client-width nil)
  (get-client-height nil)

  (round-for-scale 5.5)

  )

