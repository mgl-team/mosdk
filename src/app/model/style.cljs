(ns app.model.style)


(def text-style
  {:bold             false
   :italic           false
   :strike-through   false
   :underline        false
   :font             nil
   :font-size        10
   :color            "#000000"
   :vert-align       :baseline ;; :superScript :subscript
   :align            :default ;; left right center justified
   :spacing          0
   :background-color nil
   :high-light       nil})

(def line
  {:spacing 1.15
   :contents []})

(def paragraph-style
  {:spacing 3.15})

;;https://edu.gcfglobal.org/en/word2016/line-and-paragraph-spacing/1/

