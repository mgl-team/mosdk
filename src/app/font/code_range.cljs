(ns app.common.code-range
  (:require
   [app.font.base :as font]))

(defonce ^:private code-range (atom #{}))

(defn add-range
  ([name]
   (add-range name 99))
  ([name order]
   (swap! code-range #(remove (fn [x] (= name (:font-name x))) %))
   (swap! code-range conj {:font-name name
                           :range     (js->clj (font/character-set name))
                           :order     99})
   (swap! code-range #(sort-by :order > %))))

(defn char-code
  [v]
  (.charCodeAt v 0))

(defn code-font
  [v]
  (let [total-keys (dec (count @code-range))]
    (if (>= total-keys 0)
      (loop [index    0]
        (if (some #{v} (:range (nth @code-range index)))
          (:font-name (nth @code-range index))
          (if (< index total-keys)
            (recur (inc index))
            nil)))
      nil)))

(defn font-from-char [c]
  (-> c char-code code-font))

(comment
  (.charCodeAt "ᠡᠷᠬᠡ" 0)
  (some #{6177} chars)

  (type @code-range)
  (count @code-range)
  (nth @code-range 0)

  (add-range :white)
  (code-font 6177)
  (subs "ᠡᠷᠬᠡ" 1)
  (font-from-char (subs "ᠡᠷᠬᠡ" 1)))
