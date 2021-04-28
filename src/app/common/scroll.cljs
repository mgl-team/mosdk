(ns app.common.scroll
  (:require
   [app.common.browser :as browser]))


(defn get-client-width [elem]
  (if elem (.-clientWidth elem) 0))

(defn get-client-height [elem]
  (if elem (.-clientWidth elem) 0))

(defn round-for-scale [value]
  (if (<= (- value (js/Math.floor value)) 0.5)
    (js/Math.floor value)
    (js/Math.round value)))

(def settings
  (atom {:show                           true
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
         :arrow-dim                      (js/Math.round (* 13 browser/retina-pixel-ratio))
         ;; scroll element color
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
         :arrow-over-border-color        "#cfcfcf"
         :arrow-over-background-color    "#cfcfcf"
         :arrow-active-border-color      "#ADADAD"
         :arrow-active-background-color  "#ADADAD"

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
         :is-horizontal-scroll           false


         
         }))

(def arrow 
  (atom {:size 16
         :color-start {:r }}))

(defn arrow [])
(comment
  (get-client-width nil)
  (get-client-height nil)

  (round-for-scale 5.5)

  
  )

