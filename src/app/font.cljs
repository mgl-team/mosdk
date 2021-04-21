(ns app.font)

(def fonts (atom {}))

(defn load [name url]
  (-> (js/fetch url #js {})
      (.then (fn [resp]
               (if (>= (.-status resp) 400)
                 #(js/console.log "error on fetch")
                 (if (.-ok resp)
                   (-> (.arrayBuffer resp)
                       (.then (fn [v]
                                (let [fonta (js/window.fontkit.create v)]
                                  (swap! fonts assoc name fonta)))))
                   #(js/console.log "error on fetch")))))))

(defn get-font [name]
  (get @fonts name))

(defn units-per-em [name]
  (.-unitsPerEm (get-font name)))

(defn font-scale [name]
  (/ 1000 (units-per-em name)))

(defn layout [font-name value]
  (.layout (get-font font-name) value))

(defn glyph-width [glyph]
  (.-advanceWidth glyph))

(defn width [font-name font-size glyphs]
  (* (/ font-size 1000) 
     (apply + (map #(* (glyph-width %) 
                       (font-scale font-name)) 
                   glyphs))))

(comment
  (load :white "http://localhost:8700/fonts/mnglwhiteotf.ttf")

  (get-font :white)
  (units-per-em :white)
  (font-scale :white)
  (js/console.log (layout :white "ᠡᠷᠬᠡ"))
  (def glyphs (.-glyphs (layout :white "ᠡᠷᠬᠡ")))
  (width :white 24 glyphs)
  (prn "aa")
  ;;
  )