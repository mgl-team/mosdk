(ns editor.page
  (:require
   [app.canvas :as canvas]))

(def container (atom nil))

(defn init-element []
  (let [ele (js/document.getElementById "canvas")
        ctx (canvas/get-context ele "2d")]
    (reset! elem {:ele ele :ctx ctx})))

(defn get-ctx []
  (:ctx @elem))

(defn get-element []
  (:ele @elem))

(defn width []
  (.-width (:ele @elem)))

(defn height []
  (.-height (:ele @elem)))

(defn rect []
  (canvas/fill-rect (get-ctx) {:x 0 :y 0 :w (width) :h (height)}))

(defn init []
  (reset! container (js/document.getElementById "editor-container")))

(defn on-resize [e]
  (let [height (.-innerHeight js/window)
        width  (.-innerWidth js/window)]
    (js/console.log width height)
    (js/console.log (.-clientWidth @container)
                    (.-clientHeight @container))))
    ;; (aset (.-style container) "width" "")


(comment
  (init)
  (.getElementById js/document "canvas")
  (js/document.getElementById "canvas")

  rect
  (rect)
  (get-ctx)
  (canvas/fill-style (get-ctx) "#E2E2E2")
  (attr (get-ctx "width"))
  (def editor (js/document.getElementById "editor-container"))
  editor
  (.-innerHeight js/window)
  (->
   (aget editor "style")
   (aget "innerHeight"))

  (.addEventListener js/window "resize" #(js/console.log "resizing ...."))
  (.removeEventListener js/window "resize" #(js/console.log "resizing ...."))
  (.addEventListener js/window "resize" on-resize)
  (.removeEventListener js/window "resize" on-resize)


  container


  (require '[app.font.base :as font])
  (def font (font/get-font :white))
  font
  (font/glyph :white 275)
  (js/console.log (font/layout :white "ᠡᠷᠬᠡ"))

  (js/console.log (font/glyph :white 275))

  ;; konva
  (def konva js/window.Konva)
  (def scale (* 48 (font/font-scale :white)))
  scale
  (def glyph (font/glyph :white 225))
  (def y (+ 50 (font/width :white 48 glyph)))
  y
  (font/svg (font/glyph :white 225))
  (def args (clj->js {:x 50 :y 50 :data (font/svg (font/glyph :white 225)) :fill "green" :scale {:x scale :y (- scale)} :rotation 90}))

  (def args (clj->js {:x 50 :y y :data (font/svg (font/glyph :white 678)) :fill "green" :scale {:x scale :y (- scale)} :rotation 90}))
  args
  
  ;;
  (def stage (konva.Stage. (clj->js {:container "app" :width 400 :height 400})))
  (def layer (konva.Layer.))
  (def path (konva.Path. args))
  (.add layer path)
  (.add stage layer)
  (.draw layer)

  ;;
  (.destroyChildren layer)

  (prn "Hi"))
