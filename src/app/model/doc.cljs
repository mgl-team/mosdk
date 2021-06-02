(ns app.model.doc)

(def font
  {:name          :default
   :original-size 10
   :scale         1
   :size          10})

(def under-line
  {
   ;; SINGLE, WORD, DOUBLE, THICK, DOTTED, DOTTEDHEAV,
   ;; DASH, DASHEDHEAV, DASHLONG, DASHLONGHEAV, DOTDASH, DASHDOTHEAVY,
   ;; DOTDOTDAS, DASHDOTDOTHEAVY, WAVE, WAVYHEAVY, WAVYDOUBLE
   :type  :single
   :color "#000000"})

(def border-style
  {
   :color nil
   :style :single ;; :none :doted :dashed
   :size  1})

(def border
  {
   :left   border-style
   :right  border-style
   :top    border-style
   :bottom border-style})

(def offset
  {
   :x 10
   :y 10})


(def shadow
  {
   :color   "#000000"
   :blur    0
   :offset  offset
   :opacity 0.5})


(def spacing
  {
   :after     0
   :before    0
   :line      nil
   :line-rule :auto})  ;; exactly   at-least


(def text
  {
   :text             ""
   :bold             false
   :italic           false
   :strike           false
   :double-strike    false
   :underline        under-line
   :font             font
   :color            "#000000"
   :superscript      false
   :subscript        false
   :align            :default ;; left right center justified
   :background-color "#000000"
   :highlight        "#000000"
   :shadow           nil})

  ;;  :breaksbk

(def text-fram
  {
   :position  {:x 0
               :y 0}
   :width     0
   :height    0
   :anchor    {:horizontal :margin ;; page text
               :vertical   :margin} ;; page text

   :alignment {:x :center ;; left right inside outside
               :y :center} ;; top bottom inside outside

   :borders   {:top    nil
               :bottom nil
               :left   nil
               :right  nil}})
             ;; color: "auto", space: 1, value: "single", size: 6,



(def symbol-run
  {
   :char ""
   :font font}) ;; default Windings font

(def tab-stop
  {
   :type :left ;; bar :center :clear :decimal :end :num :right :start
   :position :max}) ;; max=9026    or less then max


(def image
  {:data ""
   :transformation {:x 0 :y 0 :flip {:vertical true
                                     :horizontal true}
                    :rotation 0}
   :floating {:x 0 :y 0 :zindex 0
              :wrap {:type :none ;; square tight top-and-bottom
                     :side :both} ;; left right largest

              :margins {:top 0 :bottom 0 :left 0 :right 0}}})

(def table-cell
  {:shading        {:color ""
                    :fill  ""
                    :val   :clear} ;;nil percent_10 - 12 15 20 25 30 35 37 40 45 5 50  55 60 62 65 70 75 80 85 87 90 95
                                            ;; reverse_diagonal_stripe solid thin_diagonal_cross
                                            ;; thin_diagonal_stripe thin_horizontal_cross
                                            ;; thin_reverse_diagonal_stripe thin_vertical_stripe vertical_stripe
                                            ;; diagonal_cross diagonal_stripe horizontal_cross horizontal_stripe
                                            ;; nil

   :margins        {:top    0
                    :left   0
                    :bottom 0
                    :right  0}
   :vertical-align :center ;;bottom top
   :vertical-merge :continue ;;restart
   :text-direction :bottom-to-top-left-to-right ;; left-to-right-top-to-bottom top-to-bottom-right-to-left
   :row-span       0
   :column-span    0
   :width          {:size 0
                    :type :auto} ;; DXA nil percentage
           ;; value is in twentieths of a point
           ;; is considered as zero
           ;; percent of table width

   :borders        {:color ""
                    :size  0
                    :style :single} ;;none outside dashed dotted inset thick wave

   :contents       []})

(def table-row
  {
   :children []
   :cant-split false
   :table-header false
   :height {:height 0 :rule :auto}}) ;; atleast exact



(def table
  {:align                  :center ;; left right start end justified both
   :layout                 :autofit ;; fixed
   :with                   {:type :auto ;; DXA nil percentage
                            :size 1}
   :style                  "" ;; style id string
   :rows                   []
   :column-widths          []
   :margins                {:bottom    0
                            :left      0
                            :right     0
                            :top       0
                            :with-type :auto} ;; dxa nil percentage

   :borders                {:bottom            {:color ""
                                                :size  0
                                                :style :single} ;;none outside dashed dotted inset thick wave

                            :top               nil
                            :left              nil
                            :right             nil
                            :inside-horizontal {:color ""
                                                :size  0
                                                :style :single}
                            :inside-vertical   {:color ""
                                                :size  0
                                                :style :single}}
   :float                  {:overlap                      :never ;; overlap
                            :right-from-text              0
                            :left-from-text               0
                            :top-from-text                0
                            :bottom-from-text             0
                            :vertical-anchor              :margin ;; page text
                            :horizontal-anchor            :margin ;; page text
                            :relative-horizontal-position :center ;; left right inside outside
                            :relative-vertical-position   :center ;; left right index outside
                            :absolute-horizontal-position 0
                            :absolute-vertaical-position  0}

   :visually-right-to-left false})


(def table-of-contents
  {:hyperlink                             true
   :caption-label                         ""
   :caption-label-including-numbers       ""
   :entries-from-bookmark                 ""
   :entry-and-page-number-separator       ""
   :heading-style-range                   ""
   :hide-tab-and-page-numbers-in-web-view false
   :page-numbers-entry-levels-range       ""
   :preserve-new-line-in-entries          false
   :preserve-tab-in-entries               false
   :seq-field-identifier-for-prefix       ""
   :sequence-and-page-numbers-separator   ""
   :styles-with-levels                    []   ;;{:level 1 :style-name ""}
   :tc-field-identifier                   ""
   :tc-field-level-range                  ""
   :use-applied-paragraph-outline-level   false})


(def indent
  {:level 0
   :type  :default})

(def paragraph
  {
   :border        border
   :heading       nil
   :spacing       nil
   :border        nil
   :outline-level 0
   :alignment     nil
   :indent        :first-line ;; start end right left hanging
   :contents      []})


(def page-numbers
  {
   :start 1
   :type  :none});;:decimal ;;

(def section
  {:cols {:num 0 :sep false :space 0
          :col {:w 720 :space 20}}
   :footer-wrapper-group nil
   :footer-reference nil
   :grid nil})

(def page
  {
   :orientation :default
   :height      100
   :width       100})


(def doc
  {:pages    []
   :settings {}
   :width    100
   :height   100})

(def bookmark
  {:contents []
   :start {:id "" :linkid ""}
   :end {:linkid ""}})
