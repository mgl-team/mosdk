(ns app.font)

(def fonts (atom {}))

(defn init []
  (def fonts (atom {})))

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

(defn get-glyphs [font-name value]
  (.-glyphs (layout font-name value)))

(defn glyph-width [glyph]
  (.-advanceWidth glyph))

(defn width [font-name font-size glyph]
  (* (/ font-size 1000)
     (glyph-width glyph)
     (font-scale font-name)))

(defn -width [font-name font-size glyphs]
  (* (/ font-size 1000) 
     (apply + (map #(* (glyph-width %) 
                       (font-scale font-name)) 
                   glyphs))))

(comment
  (load :white "http://localhost:8700/fonts/mnglwhiteotf.ttf")

  (get-font :white)
  (units-per-em :white)
  (font-scale :white)
  (js/console.log (layout :white "ᠡᠷᠬᠡ"))
  (def glyphs (.-glyphs (layout :white "ᠡᠷᠬᠡ")))
  (def glyphs (.-glyphs (layout :white "aa")))
  glyphs
  (width :white 36 glyphs)

  (width :white 48 (first glyphs))
  (cv/translate ctx 100 116.40625)

  (require '[app.canvas :as cv] :reload)
  (def canvas (js/document.getElementById "canvas"))
  canvas
  (def ctx (cv/get-context canvas "2d"))
  ctx
  (second glyphs)
  (.-path (first glyphs))
  (cv/translate ctx 110 110)
  (cv/scale ctx 1 -1)
  (cv/rotate ctx (- (* js/Math.PI 0.5)))
  (cv/begin-path ctx)
  (.render (first glyphs) ctx 48)
  (cv/close-path ctx)
  (.render (second glyphs) ctx 48)

  (cv/transform ctx 1 0 0 1 0 0)
  (cv/clear-rect ctx {:x 0 :y 0 :w 300 :h 300})

  (prn "aa")
  (.-head (get-font :white))
  (.getVariation (get-font :white) #js {:wght 0.5})
  (.-variationAxes (get-font :white))
  (.-copyright (get-font :white))

  ;;
  )