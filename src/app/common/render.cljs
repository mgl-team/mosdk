(ns app.common.render
  (:require 
   [app.font :as font]
   [app.canvas :as cv]))


(comment
  font/fonts
  (require '[app.font :as font] :reload)
  (font/init)
  (font/load :white "http://localhost:8700/fonts/mnglwhiteotf.ttf")

  (font/layout :white "ᠡᠷᠬᠡ")
  (js/console.log *1)
  (font/get-font :white)
  (font/get-glyphs :white "ᠡᠷᠬᠡ")
  (def gl *1)
  (def gl (font/get-glyphs :white "ᠡᠷᠬᠡ"))

  (def canvas (js/document.getElementById "canvas"))
  canvas
  (def ctx (cv/get-context canvas "2d"))
  ctx
  (cv/translate ctx 50 50)
  (cv/scale ctx -1 1)
  (cv/rotate ctx (* js/Math.PI 0.5))
  (.render (first glyphs) ctx 48)
  (cv/transform ctx 1 0 0 1 0 0)
  (cv/clear-rect ctx {:x -100 :y -100 :w 200 :h 200})

  (prn "aa")
  (.-head (get-font :white))
  (.getVariation (get-font :white) #js {:wght 0.5})
  (.-variationAxes (get-font :white))
  (.-copyright (get-font :white))

  ;;
  )