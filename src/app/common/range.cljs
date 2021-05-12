(ns app.common.range
  (:require 
   [app.font :as font]))

(defonce ^:private code-range (atom {}))

(defn add-range 
  [name]
  (swap! code-range assoc name (js->clj (font/character-set name))))

(defn char-code 
  [v]
  (.charCodeAt v 0))

(defn code-font
  [v]
  (let [font-keys  (keys @code-range)
        total-keys (dec (count font-keys))
        range-fn   #(get @code-range (nth font-keys %))]
    (loop [index    0]
      (if (some #{v} (range-fn index))
        (nth font-keys index)
        (if (< index total-keys)
          (recur (inc index))
          nil)))))

(defn font-from-char [c]
  (-> c char-code code-font))

(comment 
  (.charCodeAt "ᠡᠷᠬᠡ" 0)
  (some #{6177} chars)

  (count @code-range)
  (keys @code-range)
  (type (keys @code-range))
  (nth (keys @code-range) 0)
  (vals @coce-range)

  (code-font 6177)
  (subs "ᠡᠷᠬᠡ" 1)
  (font-from-char (subs "ᠡᠷᠬᠡ" 1))
  )