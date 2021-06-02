(ns app.common.browser)

(def retina-pixel-ratio (atom 1))

(def zoom (atom 1))

(def is-retina (atom false))

(def user-agent (.toLowerCase (.-userAgent js/navigator)))

(def is-android (> (.indexOf user-agent "android") -1))

(defn device-ratio []
  (.-devicePixelRatio js/window))

(defn check-zoom []

  (if (> (device-ratio) 0.1)
    (if (< (device-ratio) 1.99)
      (reset! zoom (/ (device-ratio) 1))

      (do
        (reset! zoom (/ (device-ratio) 2))
        (reset! is-retina true))))

  (let [style (-> js/document .-firstElementChild .-style)]
    (if (> (device-ratio) 0.1)
      (aset style "zoom" (/ 1.0 @zoom))
      (aset style "zoom" "normal")))

  (when @is-retina
    (reset! retina-pixel-ratio 2)))



(defn print-params []
  (prn " retina-pixel-ratio  = " @retina-pixel-ratio)
  (prn " zoom                = " @zoom)
  (prn " is-retina           = " @is-retina))


(defn convert-to-retina-value [value scale]
  (-> ((if (true? scale) * /) value @retina-pixel-ratio)
      (+ 0.5)
      Math/floor))


(comment
  user-agent
  is-android

  (device-ratio)

  (check-zoom)

  (print-params)

  (convert-to-retina-value 9 false))
