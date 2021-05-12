(ns app.model.doc)

(def cur-id 0)

(def doc 
  {:pages []
   :settings {}
   :width 100
   :height 100}
  
  )

(def ldoc
  {:contents []})


(def flowobject
  {:type :image
   :content :obj
   :text ""
   :w 0
   :h 0
   :width 10
   :height 10})

(def paragraph 
  {:id 1
   :page-num 1
   :type :paragraph
   :str ""
   ;; split long text to fit doc height
   :text []})

(def para-line
  {:content []})

(def para-str
  {:str ""
   :width 10
   :height 10
   :page 1
   :line 2
   :style {}})