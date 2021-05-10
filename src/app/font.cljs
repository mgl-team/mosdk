(ns app.font
  (:require 
   [goog.object :as gobj]))

(def fonts (atom {}))

(defn load [name url]
  (-> (js/fetch url #js {})
      (.then (fn [resp]
               (if (>= (.-status resp) 400)
                 #(js/console.log "error on fetch")
                 (if (.-ok resp)
                   (-> (.arrayBuffer resp)
                       (.then (fn [v]
                                (let [fonta (js/window.fontkit.create v)]
                                  (swap! fonts assoc name fonta)))))
                   #(js/console.log "error on fetch")))))))

(defn get-font [name]
  (get @fonts name))

(defn units-per-em [name]
  (.-unitsPerEm (get-font name)))

(defn font-scale [name]
  (/ 1000 (units-per-em name)))

(defn layout [font-name value]
  (.layout (get-font font-name) value))

(defn render [ctx glyph font-size]
  (.render glyph ctx font-size))

(defn get-glyphs [font-name value]
  (if value
    (gobj/get (layout font-name value) "glyphs")
    nil))

(defn glyph-width [glyph]
  (if glyph
    (gobj/get glyph "advanceWidth")
    0))

(defn width [font-name font-size glyph]
  (* (/ font-size 1000)
     (glyph-width glyph)
     (font-scale font-name)))

(defn glyph [font id]
  (.getGlyph (get-font font) id))

(defn glyph-ids [font value]
  (map #(gobj/get % "id") (get-glyphs font value)))

(comment
  (load :white "http://localhost:8700/fonts/monbaiti.ttf")
  load

  (get-font :white)
  
  (units-per-em :white)
  (font-scale :white)
  (js/console.log (layout :white "ᠡᠷᠬᠡ"))
  (def glyphs (.-glyphs (layout :white "ᠡᠷᠬᠡ ")))
  (def glyphs (.-glyphs (layout :white "aa")))
  glyphs

  (width :white 48 (first glyphs))
  (cv/translate ctx 11.3671875 0)

  (require '[app.canvas :as cv] :reload)
  (def canvas (js/document.getElementById "canvas"))
  canvas
  (def ctx (cv/get-context canvas "2d"))
  ctx
  (js/console.log (last glyphs))
  (type (second glyphs))
  (second glyphs)
  (.-path (first glyphs))
  (cv/translate ctx 100 100)
  (cv/scale ctx 1 -1)
  (cv/rotate ctx (- (* js/Math.PI 0.5)))
  (cv/begin-path ctx)
  (.render (first glyphs) ctx 48)
  (cv/close-path ctx)
  (.render (second glyphs) ctx 48)
  (.render (last glyphs) ctx 48)
  (cv/fill ctx)

  (cv/translate ctx -30 -30)
  (cv/fill-text ctx "abc")
  (cv/move-to ctx 0 0)
  (cv/line-to ctx 10 10)
  (cv/stroke ctx)
  (cv/close-path ctx)

  (cv/translate ctx 0 0)
  (cv/transform ctx 1 0 0 1 0 0)
  (cv/clear-rect ctx {:x 0 :y 0 :w 100 :h 100})

  (prn "aa")
  (.-head (get-font :white))
  (.getVariation (get-font :white) #js {:wght 0.5})
  (.-variationAxes (get-font :white))
  (.-copyright (get-font :white))

  (cv/translate ctx 10 10)
  (cv/text ctx {:text "abcd" :x 10 :y 20})
  (cv/close-path ctx)

  ;;
  ;; (defn fill-commands [commands]
  ;;   (doseq [x commands]
  ;;     (())))
  (js/console.log (-> glyphs first .-path))
  (def scale (-> 48 (* (font-scale :white) 48) (/ 1000)))
  scale
  (cv/scale ctx scale scale)
   ((-> glyphs first .-path .toFunction) ctx)
  (cv/close-path ctx)
  (cv/fill ctx)
  (cv/clear-rect ctx {:x 0 :y 0 :w 100 :h 100})
  
  ;;
  )