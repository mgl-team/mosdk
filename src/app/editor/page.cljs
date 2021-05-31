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
  (prn "Hi"))
