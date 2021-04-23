(ns app.editor.page
  (:require
   [app.canvas :as canvas]))

(def elem (atom nil))

(defn init []
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

(defn )
(comment
  (init)
  (.getElementById js/document "canvas")
  (js/document.getElementById "canvas")

  rect
  (rect)
  (get-ctx)
  (canvas/fill-style (get-ctx) "#E2E2E2")
  (attr (get-ctx "width"))
  (prn "Hi"))