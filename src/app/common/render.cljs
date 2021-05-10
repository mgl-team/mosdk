(ns app.common.render
  (:require 
   [app.font :as font]
   [app.canvas :as cv]
   [goog.object :as gobj]))

(defn element [id]
  (js/document.getElementById id))

(defn get-ctx [el]
  (cv/get-context el "2d"))

(defn scale-rotate [ctx]
  (cv/scale ctx 1 -1)
  (cv/rotate ctx (- (* js/Math.PI 0.5))))

(defn render-glyph [ctx glyph font-size]
  (cv/begin-path ctx)
  (font/render ctx glyph font-size)
  (cv/close-path ctx))

(defn render-string [ctx value font font-size]
  (loop [x 0
         v (js->clj (font/get-glyphs font value))]
    (if (not-empty v)
      (do
        (cv/translate ctx x 0)
        (render-glyph ctx (first v) font-size)
        (recur
         (font/width font font-size (first v))
         (rest v)))
      x)))

(comment
  
  (def ctx (-> "canvas" element get-ctx)) 
  ctx
  (scale-rotate ctx)
  (cv/translate ctx 0 50)
  (cv/save ctx)
  (render-string ctx "ᠡᠷᠬᠡ" :white 48)
  (cv/restore ctx)
  (cv/clear-rect ctx {:x 0 :y 0 :w 100 :h 100})

  (require '[app.canvas :as cv])
  (require '[app.font :as font] :reload)
  (font/init)
  (font/load :white "http://localhost:8700/fonts/monbaiti.ttf")
  font/fonts
  (font/glyph :white 225)
  (font/layout :white "ᠡᠷᠬᠡ")

  (rest *1)
  (map #(gobj/get % "id") (font/get-glyphs :white "ᠡᠷᠬᠡ"))
  (def layout *1)
  (js/console.log *1)
  (font/get-font :white)
  (font/get-glyphs :white "ᠡᠷᠬᠡ")
  (js->clj *1)
  (rest *1)
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