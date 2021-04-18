(ns app.glyph
  (:require
   [app.fontkit :as fontkit]))

(defn width [glyph]
  (.advanceWidth glyph))

;; (apply + (map width glyphs))

(defn render [glyph ctx size]
  (.render glyph ctx size))