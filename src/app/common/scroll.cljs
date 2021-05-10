(ns app.common.scroll
  (:require
   [app.common.browser :as browser]
   [app.canvas :as cv]
   [app.state :refer [setv getv]]))


(defn get-client-width [elem]
  (if elem (.-clientWidth elem) 0))

(defn get-client-height [elem]
  (if elem (.-clientWidth elem) 0))

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

    (cv/shadow-color ctx "rgba(0,0,0,0)")

    (cv/move-to ctx 0 (- (/ h 2)))
    (cv/line-to ctx (- (/ side 2)) (/ h 2))
    (cv/line-to ctx (/ side 2) (/ h 2))
    (cv/line-to ctx 0 (- (/ h 2)))
    (cv/fill ctx)

    (cv/close-path ctx)

    (cv/restore ctx)))

(defn create-canvas-element []
  (js/document.createElement "canvas"))

(defn round-for-scale [value]
  (if (<= (- value (js/Math.floor value)) 0.5)
    (js/Math.floor value)
    (js/Math.round value)))

(defn draw-arrow [size is-retina]
  (let [dpr @browser/retina-pixel-ratio
        round-dpr (round-for-scale @browser/retina-pixel-ratio)
        isize (js/Math.floor (* size dpr))
        arrow-element (create-canvas-element)
        ctx (cv/get-context arrow-element "2d")]

    (set! (.-width arrow-element) isize)
    (set! (.-height arrow-element) isize)

    ;; (cv/shadow-color ctx "rgba(0,0,0,0)")

    (cv/begin-path ctx)
    (cv/stroke-style ctx "#cfcfcf")
    (cv/stroke-width ctx round-dpr)
    (let [x (- isize round-dpr)]
      (cv/rect ctx {:x 0 :y 0 :w x :h x}))

    (cv/stroke ctx)


    (cv/fill-style ctx "#adadad")
    (let [size-len (-> isize (/ 3) (* 1.5) (round-for-scale))
          x (round-for-scale (/ isize 2))]
      (draw-eq-triangle ctx size-len x x))

    ;; return new element
    arrow-element))

(defn draw-piper-image []
  (let [dpr             @browser/retina-pixel-ratio
        len1            (js/Math.floor (* 13 dpr))
        len2            (js/Math.floor (* 5 dpr))

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
          (cv/stroke ctx-hor))))

    [piper-image-ver piper-image-hor]))

(defn element-by-id [id]
  (js/document.getElementById id))


(def obj (atom {}))
(def settings (atom {}))

(defn- set-dimension [h w]
  (let [dpr @browser/retina-pixel-ratio
        dpr-h (js/Math.round (* h dpr))
        dpr-w (js/Math.round (* w dpr))]
    (if (and (= (:canvash @obj) dpr-h) 
             (= (:canvasw @obj) dpr-w))
      (setv obj :is-resize-arrows false)
      (do
        (setv obj :is-resize-arrows true)
        (setv obj :canvash dpr-h)
        (setv obj :canvasw dpr-w)
        (setv obj :canvas-originalh h)
        (setv obj :canvas-originalw w)
        (set! (.-width (getv obj :canvas)) dpr-w)
        (set! (.-height (getv obj :canvas)) dpr-h)))))

(defn set-scroll-hw []
  (let [dpr @browser/retina-pixel-ratio]
    (when (get @settings :is-vertical-scroll)
      (setv obj [:scroller :x] (round-for-scale dpr))
      (setv obj [:scroller :w] (js/Math.round (* dpr (- (getv obj :canvas-originalw) 1))))

      ;;init arrow image()
      (when (getv settings :show-arrows)
        nil))
    (when (getv settings :is-horizontal-scroll)
      (setv obj [:scroller :y] (round-for-scale dpr))
      (setv obj [:scroller :h] (js/Math.round (-> (getv obj :canvas-originalh) (- 1) (* dpr))))

      ;;init arrow image()
      (when (getv settings :show-arrows)
        nil))))

(defn recalc-scroller
  [pos]
  (let [dpr @browser/retina-pixel-ratio]
    (when (getv settings :is-vertical-scroll)
      (if (:show-arrows @settings)
        (do
          (setv obj :vertical-track-height (- (getv obj :canvash) (* 2 (getv obj :arrow-position))))
          (setv obj [:scroller :y] (getv obj :arrow-position)))
        (do
          (setv obj :vertical-track-height (getv obj :canvash))
          (setv obj [:scroller :y] (js/Math.round dpr))))


      (let [percent-in-view (-> (* (getv obj :max-scrolly) dpr)
                                (+ (getv obj :panel-height))
                                (/ (getv obj :panel-height)))]
        (setv obj [:scroller :h] (-> (* (/ 1 percent-in-view)
                                        (/ (getv obj :vertical-track-height) dpr))
                                     (js/Math.ceil)
                                     (* dpr)
                                     (js/Math.ceil))))
      (let [scroll-minh (-> (getv settings :scroller-min-height)
                            (* dpr)
                            (js/Math.round))]

        (when (< (getv obj [:scroller :h]) scroll-minh)
          (setv obj [:scroller :h] scroll-minh))

        (when (> (getv obj [:scroller :h]) (getv settings :scroller-max-height))
          (setv obj [:scrooler :h] (getv settings :scroller-max-height))))

      (let [scroll-coeff (/ (getv obj :max-scrolly)
                            (max 1 (getv obj :panel-height) (getv obj [:scroller :h])))]
        (when pos
          (setv obj [:scroller :y] (/ pos (+ scroll-coeff (getv obj :arrow-position))))))

      (setv obj :drag-maxy (+ 1 (- (getv obj :canvash) (getv obj :arrow-position) (getv obj [:scroller :h]))))
      (setv obj :drag-miny (getv obj :arrow-position)))

    (when (getv settings :is-horizontal-scroll)
      (if (:show-arrows @settings)
        (do
          (setv obj :horizontal-track-height (- (getv obj :canvasw) (* 2 (getv obj :arrow-position))))
          (setv obj [:scroller :x] (getv obj :arrow-position)))
        (do
          (setv obj :horizontal-track-height (getv obj :canvasw))
          (setv obj [:scroller :x] (js/Math.round dpr))))


      (let [percent-in-view (-> (* (getv obj :max-scrollx) dpr)
                                (+ (getv obj :panel-width))
                                (/ (getv obj :panel-width)))]
        (setv obj [:scroller :w] (-> (* (/ 1 percent-in-view)
                                        (/ (getv obj :horizontal-track-height) dpr))
                                     (js/Math.ceil)
                                     (* dpr)
                                     (js/Math.ceil))))
      (let [scroll-minw (-> (getv settings :scroller-min-width)
                            (* dpr)
                            (js/Math.round))]

        (when (< (getv obj [:scroller :w]) scroll-minw)
          (setv obj [:scroller :w] scroll-minw))

        (when (> (getv obj [:scroller :w]) (getv settings :scroller-max-width))
          (setv obj [:scrooler :w] (getv settings :scroller-max-width))))

      (let [scroll-coeff (/ (getv obj :max-scrollx)
                            (max 1 (getv obj :panel-width) (getv obj [:scroller :w])))]
        (when pos
          (setv obj [:scroller :x] (/ pos (+ scroll-coeff (getv obj :arrow-position))))))

      (setv obj :drag-maxx (+ 1 (- (getv obj :canvasw) (getv obj :arrow-position) (getv obj [:scroller :w]))))
      (setv obj :drag-minx (getv obj :arrow-position)))))

      


(defn scroll-init [eid settings]
  (let [holder  (element-by-id eid)
        canvas  (.appendChild holder (create-canvas-element))
        context (cv/get-context canvas "2d")
        dpr     @browser/retina-pixel-ratio]
    (aset (.-style canvas) "width" "100%")
    (aset (.-style canvas) "height" "100%")
    (aset (.-style canvas) "zindex" "100")
    (aset (.-style canvas) "top" "0px")
    (aset (.-style canvas) "webkitUserSelect" "none")

    (assoc @obj :canvas canvas)
    (assoc @obj :context context)
    (set-dimension (.-clientHeight holder) (.-clientWidth holder))

    (let [maxy (max 0 (- (get @settings :contenth) (get @settings :screenh)))]
      (assoc @obj :max-scrolly maxy)
      (assoc @obj :max-scrolly2 maxy)

      (when (and (get @settings :is-vertical-scroll) (get @settings :always-visible))
        (aset (.-style canvas) "display" (if (= 0 maxy) "none" ""))))

    (let [maxx (max 0 (- (get @settings :contentw) (get @settings :screenw)))]
      (assoc @obj :max-scrollx maxx)
      (assoc @obj :max-scrollx2 maxx)

      (when (and (get @settings :is-horizontal-scroll) (get @settings :always-visible))
        (aset (.-style canvas) "display" (if (= 0 maxx) "none" ""))))
    
    (set-scroll-hw)
    (assoc @obj :arrow-position
           (if (get @settings :show-arrows)
             (js/Math.round (+ (get @settings :arrow-dim) (round-for-scale dpr) (round-for-scale dpr)))
             (js/Math.round dpr)))
    
    (assoc @obj :panel-height (- (get @obj :canvash) (* 2 (get @obj :arrow-position))))
    (assoc @obj :panel-width (- (get @obj :canvasw) (* 2 (get @obj :arrow-position))))
    
    (recalc-scroller nil)))


(comment
  (get-client-width nil)
  (get-client-height nil)

  (round-for-scale 5.5)
  (round-for-scale @browser/retina-pixel-ratio)



  (require '[app.canvas :as cv] :reload)

  cv/shadow-color
  (def canvas (js/document.getElementById "canvas"))
  canvas
  (def ctx (cv/get-context canvas "2d"))
  ctx
  (cv/fill-style ctx "#ADADAD")
  (cv/stroke-style ctx "#adadad")
  (draw-eq-triangle ctx 26 60 60)

  (cv/clear-rect ctx {:x 0 :y 0 :w 200 :h 200})


  (def arrow (draw-arrow 26 nil))
  arrow

  (cv/shadow-color ctx "rgba(0,0,0,0)")
  (cv/draw-image ctx arrow {:x 10 :y 10 :w 26 :h 26})
  (cv/draw-image ctx arrow 10  10)

  (require '[app.state :refer [setv getv]])

  (prn "aa")
  (bit-xor 1 2))


