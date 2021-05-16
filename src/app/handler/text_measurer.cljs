(ns app.handler.text-measurer
  (:require 
   [app.font.base :as font]))


(defn text-width [font size text]
  (apply + 
         (map #(font/width font size %) 
              (font/get-glyphs font text))))

(def height font/height-of-font)

(comment
  (def text "ᠡᠷᠬᠡ")
  (text-width :white 14 text)
  (height :white 14 text))