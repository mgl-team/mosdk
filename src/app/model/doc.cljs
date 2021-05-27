(ns app.model.doc)

(def font
  {:name          :default
   :original-size 10
   :scale         1
   :size          10})

(def border-style
  {
   :color nil
   :space 1
   :style :single
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
   :underline        {:type  :default
                      :color "#000000"}
   :font             font
   :color            "#000000"
   :superscript      false
   :subscript        false
   :align            :default ;; left right center justified
   :background-color "#000000"
   :highlight        "#000000"
   :shadow           nil
   :breaks           0})

(def symbol-run
  {
   :char ""
   :font font}) ;; default Windings font



(def bound
  {:x 0
   :y 0
   :w 0
   :h 0})

(def flowobject
  {:bound   bound
   :type    :default
   :content nil
   :w       0
   :h       0})

(def element
  {:type    :text ;; :flow :table
   :content nil
   :style   {}
   :pos     0
   :width   0
   :height  0})

(def cell-style
  {})

(def cell
  {:style   cell-style
   :content nil
   :w       0
   :h       0})

(def table
  {:rows    0
   :columns 0
   :cells   []})

(def part
  {:bound    bound
   :elements []})

(def column
  {:baseline 0
   :parts    []})

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

(def cursor
  {:x  0
   :y  0
   :px 0
   :py 0})
