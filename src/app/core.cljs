(ns app.core
  (:require
   [rum.core :as rum]
   [app.state :as state]
   [app.canvas :as canvas]
   ))

(defonce app-state (atom {:text "Hello world!"}))

(rum/defc page
  []
  [:div.border-collapse.table-fixed.h-full.w-full
   [:div#viewport-hbox-layout.absolute.h-full.w-full
    [:#editor-container
     [:canvas#canvas]]]])

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
