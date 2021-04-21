(ns app.fontkit
  (:require
   ["fontkit" :as fontkit]))

(def fonts (atom {}))
(def variations (atom {}))

(defn open [id file]
  (let [font (.openSync fontkit file)]
    (swap! fonts :conj {id font})))

(defn variation [font opts]
  (.variation font opts))

(defn layout [font string]
  (.layout font string))

(defn width [glyph]
  (.advanceWidth glyph))

(defn width-of-string [font string]
  "width of string"
  (let [glyphs (layout font string)]
    (apply + (map width glyphs))))

;; (apply + (map width glyphs))

(defn render [glyph ctx size]
  (.render glyph ctx size))

