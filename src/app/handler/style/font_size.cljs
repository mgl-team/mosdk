(ns app.handler.style.font-size)

(def sizes [8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72])

(defn font-size-out
  " value is old value "
  [value]
  (cond
    (<= value (first sizes))
    (max 1 (js/Math.floor (dec value)))

    (> value (last sizes))
    (if (<= value (-> (last sizes) 
                      (/ 10) 
                      inc 
                      (* 10) 
                      js/Math.floor))
      (last sizes)
      (-> value 
          (/ 10) 
          js/Math.ceil 
          dec 
          js/Math.floor 
          (* 10)))

    :else
    (loop [index (dec (count sizes))]
      (if (> value (nth sizes index))
        (nth sizes index)
        (recur (dec index))))))

(defn font-size-in
  " value is old value "
  [value]
  (cond
    (< value (first sizes))
    (if (>= value (dec (first sizes)))
      (first sizes)
      (js/Math.floor (inc value)))

    (>= value (last sizes))
    (min 300 (-> value 
                 (/ 10) 
                 inc 
                 js/Math.floor 
                 (* 10)))

    :else
    (loop [index 0]
      (if (< value (nth sizes index))
        (nth sizes index)
        (recur (inc index))))))


(comment
  (font-size-in 1)
  (font-size-out 9))