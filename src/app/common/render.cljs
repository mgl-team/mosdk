(ns app.common.render
  (:require 
   [app.font :as font]
   [app.canvas :as cv]
   [goog.object :as gobj]))


(comment
  font/fonts
  (require '[app.font :as font] :reload)
  (font/init)
  (font/load :white "http://localhost:8700/fonts/mnglwhiteotf.ttf")

  (font/layout :white "ᠡᠷᠬᠡ")
  (def layout *1)
  (js/console.log *1)
  (font/get-font :white)
  (font/get-glyphs :white "ᠡᠷᠬᠡ")
  (def gl *1)
  (def gl (font/get-glyphs :white "ᠡᠷᠬᠡ"))

  (def canvas (js/document.getElementById "canvas"))
  canvas
  (def ctx (cv/get-context canvas "2d"))
  ctx
  
  (require '[goog.object :as gobj])

  (def layout (font/layout :white "ᠡᠷᠬᠡ"))
  (gobj/get (font/layout :white "ᠡᠷᠬᠡ") "glyphs")

  ;;
  )