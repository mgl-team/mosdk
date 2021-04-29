(ns app.common.scroll
  (:require
   [app.common.browser :as browser]
   [app.canvas :as cv]))


(defn get-client-width [elem]
  (if elem (.-clientWidth elem) 0))

(defn get-client-height [elem]
  (if elem (.-clientWidth elem) 0))

(defn round-for-scale [value]
  (if (<= (- value (js/Math.floor value)) 0.5)
    (js/Math.floor value)
    (js/Math.round value)))

(defn default-settings []
  {:show                           true
   :screen-w                       -1
   :screen-h                       -1
   :content-w                      0
   :content-h                      0
   :initial-delay                  300
   :arrow-repeat-freq              70
   :scroll-page-percent            (/ 1.0 8)
   :scroller-min-height            34
   :scroller-max-height            99999
   :scroller-min-width             24
   :scroller-max-width             99999
   :arrow-dim                      (js/Math.round (* 13 @browser/retina-pixel-ratio))
   ;;  scroll element color
   :scroller-color                 241
   :scroll-background-color        "#f4f4f4"
   :scroll-background-color-hover  "#f4f4f4"
   :scroll-background-color-active "#f4f4f4"
   :stroke-style-none              "#cfcfcf"
   :stroke-style-over              "#cfcfcf"
   :stroke-style-active            "#ADADAD"
   ;; scroll speed
   :vscroll-step                   10
   :hscroll-step                   10
   :wheel-scroll-lines             1

   ;; arrow element color
   :arrow-color                    173
   :arrow-background-color         241
   :arrow-border-color             "#cfcfcf"
   :arrow-border-color-over        "#cfcfcf"
   :arrow-background-color-over    "#cfcfcf"
   :arrow-border-color-active      "#ADADAD"
   :arrow-background-color-active  "#ADADAD"

   ;; scroll animation time delay
   :fade-in-fade-out-delay         20

   ;; stripes color
   :piper-color                    "#cfcfcf"
   :piper-color-hover              "#f1f1f1"

   :default-color                  241
   :hover-color                    207
   :active-color                   173

   :arrow-size-w                   (js/Math.round (* 13 @browser/retina-pixel-ratio))
   :arrow-size-h                   (js/Math.round (* 13 @browser/retina-pixel-ratio))
   :corner-radius                  0
   :flim-scroll                    false
   :always-visible                 false
   :is-vertical-scroll             true
   :is-horizontal-scroll           false})

(defn default-arrow [settings]
  {:size                    16
   :is-retina               false
   :color-start             (:arrow-color settings)
   :color-border-none       (:arrow-border-color settings)
   :color-border-over       (:arrow-border-color-over settings)
   :color-border-active     (:arrow-border-color-active settings)

   :color-background-none   (:arrow-background-color settings)
   :color-background-over   (:arrow-background-color-over settings)
   :color-background-active (:arrow-background-color-active settings)

   :is-draw-border          true

   :image-left              nil
   :image-top               nil
   :image-right             nil
   :image-bottom            nil

   :fade-in-fade-out-delay  (or (:fade-in-fade-out-delay settings) 30)})

;; https://stackoverflow.com/questions/8935930/create-equilateral-triangle-in-the-middle-of-canvas
;; 
;; 
    
(defn draw-eq-triangle [ctx side x y]
  (let [h (* side (js/Math.cos (/ js/Math.PI 6)))]
    (cv/save ctx)

    (cv/translate ctx x y)

    (cv/begin-path ctx)

    (cv/move-to ctx 0 (- (/ h 2)))
    (cv/line-to ctx (- (/ side 2)) (/ h 2))
    (cv/line-to ctx (/ side 2) (/ h 2))
    (cv/line-to ctx 0 (- (/ h 2)))
    (cv/fill ctx)

    (cv/close-path ctx)

    (cv/restore ctx)))

(defn create-canvas-element []
  (js/document.createElement "canvas"))

(defn draw-arrow [size]
  )

(defn draw-piper-image []
  (let [dpr             @browser/retina-pixel-ratio
        len1            (js/Math.floor 13 dpr)
        len2            (js/Math.floor 5 dpr)

        piper-image-ver (create-canvas-element)
        piper-image-hor (create-canvas-element)

        ctx-ver         (cv/get-context piper-image-ver "2d")
        ctx-hor         (cv/get-context piper-image-hor "2d")

        -dpr            (if (< dpr 0.5) (js/Math.ceil dpr) (js/Math.round dpr))
        line-width      (js/Math.floor dpr)]

    (set! (.-width piper-image-ver) len2)
    (set! (.-height piper-image-ver) len1)

    (set! (.-width piper-image-hor) len1)
    (set! (.-height piper-image-hor) len2)

    ;; add behind elements
    (doseq [x [ctx-ver ctx-hor]] (cv/composition-operation x "destination-over"))
    (doseq [x [ctx-ver ctx-hor]] (cv/fill-style x "#D7D7D7"))
    (doseq [x [ctx-ver ctx-hor]] (cv/stroke-width x line-width))

    (cv/fill-rect ctx-ver {:x 0 :y 0 :w len2 :h len1})
    (cv/fill-rect ctx-hor {:x 0 :y 0 :w len1 :h len2})

    (let [steps (map #(+ % -dpr (js/Math.floor dpr)) (range 0 7))]
      (doseq [step steps]
        (let [y (+ step (* 0.5 line-width))]
          (cv/move-to ctx-ver 0 y)
          (cv/line-to ctx-ver len2 y)
          (cv/stroke ctx-ver)))

      (doseq [step steps]
        (let [x (+ step (* 0.5 line-width))]
          (cv/move-to ctx-hor x 0)
          (cv/line-to ctx-hor x len2)
          (cv/stroke ctx-hor))))))

(defn init-arrow [size-w size-h is-retina]
  nil)



(comment
  (get-client-width nil)
  (get-client-height nil)

  (round-for-scale 5.5)


  (require '[app.canvas :as cv])
  (def canvas (js/document.getElementById "canvas"))
  canvas
  (def ctx (cv/get-context canvas "2d"))
  ctx
  (cv/fill-style ctx "#ADADAD")
  (draw-eq-triangle ctx 30 20 20)
  (cv/stroke ctx)
  "aa"
  *1

  (cv/clear-rect ctx {:x 0 :y 0 :w 100 :h 100})

  (take 7 (range 0 8))
  (js/Math.floor 2.0)

(range 0 7)
  (map #(+ % (* 0.5 2)) (range 0 7))
  )

