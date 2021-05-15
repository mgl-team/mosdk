(ns app.model.doc)

(def font
  {:name          :default
   :original-size 10
   :scale         1
   :size          10})

(def text-style
  {:bold             false
   :italic           false
   :strike-through   false
   :underline        false
   :font             font
   :color            "#000000"
   :vert-align       :baseline ;; :superScript :subscript
   :align            :default ;; left right center justified
   :background-color "#000000"
   :high-light       "#000000"})
   
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

(def range
  {:x        0
   :y        0
   :w        0
   :h        0
   :elements []})

(def column
  {:baseline 0
   :ranges   []})

(def indent 
  {:level 0
   :type  :default})

(def paragraph
  {:bound        bound
   :space        1.15
   :space-after  3.5
   :space-before 0
   :indent       indent
   :columns      []})

(def page 
  {:bound {}
   :paras []
   :flows []})

(def doc
  {:pages    []
   :settings {}
   :width    100
   :height   100})