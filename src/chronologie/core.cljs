(ns chronologie.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [<!]]
            [chronologie.ocda :refer [get-specs]])
  (:import [goog.ui IdGenerator]))

(enable-console-print!)

; Utilities

(defn guid []
  (.getNextUniqueId (.getInstance IdGenerator)))

(defn indices [pred coll]
  (keep-indexed #(when (pred %2) %1) coll))

(defn find-card-index [card v]
  (first (indices #(= (:id %1) (:id card)) v)))

(defn valid-timeline? [timeline]
  (let [get-date #(:date %1)
        stripped (filter get-date timeline)
        sorted (sort-by get-date stripped)]
    (= sorted stripped)))

(defn empty-card []
  {:id (guid)})

(defn space-timeline [timeline]
   (-> (filter #(:date %1) timeline)
       (interleave (repeatedly empty-card))
       (conj (empty-card))
       (vec)))

(defn discard-card [app]
  (assoc app :deck (vec (rest (:deck app)))))

(defn place-card [app target]
  (let [card (get-in app [:deck 0])
        target-pos (find-card-index target (:timeline app))]
    (-> app
        (assoc-in [:timeline target-pos] card)
        (update-in [:timeline] space-timeline)
        (discard-card)
        (update-in [:score] inc))))

(defn valid-move? [app target]
  (let [new-state (place-card app target)]
    (valid-timeline? (:timeline new-state))))

(defn game-over? [app]
  (-> app :deck empty?))

; Application state

(def some-state (atom {:deck (shuffle [{:id (guid)
                                      :title "mies"
                                      :year 2010}
                                     {:id (guid)
                                      :title "aap"
                                      :year 1990}
                                      {:id (guid)
                                      :title "jezus"
                                      :year 0}])
                      :timeline (space-timeline [{:id (guid)
                                                  :title"noot"
                                                  :year 2000}])
                      :score 0}))

(def empty-state {:deck []
                  :timeline (space-timeline [])
                  :score 0})

(def example-card-specs [["rijksmuseum" "6b4118e96e1b25b376c46086c4c0e00898ff13e2"
                                        "d8ee6313f6abe6ab37d72c2691281cf1237b94e7"
                                        "22d3d3d1a6a11177841602e8e9eb15f797949151"
                                        "0618c5188f2fb2c48cdc7d8e27c17276befdc813"
                                        "c0e90ba269dcbcfc7075512935c6b8fab1ead4d4"]])

(defn new-game [card-specs]
  (let [app-state (atom empty-state)
        set-cards #(assoc %1 :deck %2)]
    (go (->> (<! (get-specs card-specs))
             (swap! app-state set-cards)))
    app-state))

(def app-state (new-game example-card-specs))

; Event handlers

(defn handle-card-place [target]
  (if (valid-move? @app-state target)
    (swap! app-state place-card target)
    (swap! app-state discard-card)))

; Components

(defmulti card-view (fn [card _] (contains? card :title)))

(defmethod card-view true [card owner]
  (let [get-media-url #(-> %1 :media_urls first :url)]
    (dom/li #js {:className "card"}
            (dom/div nil
                     (dom/div #js {:className "frame"}
                              (dom/img #js {:src (get-media-url card)})))
                     (dom/div #js {:className "meta"}
                              (dom/span #js {:className "title"} (:title card))
                              (dom/span #js {:className "date"} (:date card))))))

(defmethod card-view false [card owner]
  (dom/li #js {:id (:id card)
               :className "card"
               :onClick (fn [_] (handle-card-place @card))}
          (dom/div nil
                   (dom/div #js {:className "frame empty"}
                            (dom/span nil "?")))))

(defn timeline-view [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (dom/h2 nil "Timeline")
        (apply dom/ul #js {:className "timeline"}
               (om/build-all card-view (:timeline app)))))))

(defn deck-view [deck owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (dom/h2 nil "Next card:")
        (om/build card-view (deck 0))))))

(om/root
  (fn [app owner]
    (dom/div nil
             (dom/p nil (str "Score: " (:score app)))
             (if (-> app game-over? not)
                 (om/build deck-view (:deck app)))
             (om/build timeline-view app)))
  app-state
  {:target (. js/document (getElementById "app"))})

(-> @app-state :deck first)
