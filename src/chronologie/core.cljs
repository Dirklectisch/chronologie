(ns chronologie.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true])
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
  (let [get-year #(:year %1)
        stripped (filter get-year timeline)
        sorted (sort-by get-year stripped)]
    (= sorted stripped)))

(defn empty-card []
  {:id (guid)})

(defn space-timeline [timeline]
   (-> (filter #(:year %1) timeline)
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

(def app-state (atom {:deck (shuffle [{:id (guid)
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


; Event handlers

(defn handle-card-place [target]
  (if (valid-move? @app-state target)
    (swap! app-state place-card target)
    (swap! app-state discard-card)))

; Components

(defmulti card-view (fn [card _] (contains? card :title)))

(defmethod card-view true [card owner]
  (dom/li #js {:id (:id card)} (str (:title card) ", " (:year card))))

(defmethod card-view false [card owner]
  (dom/li #js {:id (:id card) :onClick (fn [_] (handle-card-place @card))} "insert"))

(defn timeline-view [app owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
        (dom/h2 nil "Timeline")
        (apply dom/ul nil
               (om/build-all card-view (:timeline app)))))))

(om/root
  (fn [app owner]
    (dom/div nil
             (dom/p nil (str "Score: " (:score app)))
             (if (-> app game-over? not)
               (dom/div nil
                        (dom/h2 nil "Next card:")
                        (om/build card-view (get-in app [:deck 0]))))
             (om/build timeline-view app)))
  app-state
  {:target (. js/document (getElementById "app"))})
