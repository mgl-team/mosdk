(ns app.core
  (:require
   [rum.core :as rum]
   [app.state :as state]
   [app.canvas :as canvas]
   ))

(defonce app-state (atom {:text "Hello world!"}))

(rum/defcs page
  <
  rum/reactive
  {:did-mount (fn [state]
                (let [canv (.getElementById js/document "canvas")
                      ctx (canvas/get-context canv "2d")]
                  (.translate ctx (/ (.-width canv) 2) (/ (.-height canv) 2))
                  (.scale ctx -1 1)

                  (.rotate ctx (+ (/ js/Math.PI 2)))
                  (canvas/text ctx {:text "ClojureScript is a compiler for Clojure that targets JavaScript. It emits JavaScript code which is compatible with the advanced compilation mode of the Google Closure optimizing compiler." :x 0 :y 0}))
                state)}
  []

  [:canvas#canvas.flex.min-h-screen])

(defn start []
  ;; start is called by init and after code reloading finishes
  ;; this is controlled by the :after-load in the config
  (rum/mount (page)
             (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
