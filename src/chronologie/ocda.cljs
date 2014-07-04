(ns chronologie.ocda
  (:require-macros [cljs.core.async.macros :refer [go alt!]])
  (:require [cljs.core.async :refer [>! <! put! chan]]
            [goog.net.XhrIo :as xhr]))

(enable-console-print!)

(defn get-json [url]
  (let [out (chan 1)
        read-json #(-> %1
                       .-target
                       .getResponseJson
                       js->clj)]

    (xhr/send url
              (fn [e] (put! out (read-json e))))
    out))

(defn get-json-bulk [& urls]
  (go (let [chans (vec (map get-json urls))]
          (loop [i 0
                 res []]
            (if (= i (count chans))
              res
              (recur (inc i) (conj res (<! (chans i)))) )))))

(def base-url "http://api.opencultuurdata.nl/v0/")

(defn object-url
  [source object]
  (str base-url source "/" object))

(defn spec->urls
  [spec]
  (let [source (first spec)
        ids (rest spec)]
    (map #(object-url source %1) ids)))

(defn specs->urls
  [specs]
  (flatten (map spec->urls specs)))

(defn get-specs
  [specs]
  (apply get-json-bulk (specs->urls specs)))

(def test-urls ["http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2"
                "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2"])

(comment

  (def example-card-specs [["rijksmuseum" "6b4118e96e1b25b376c46086c4c0e00898ff13e2"
                                        "d8ee6313f6abe6ab37d72c2691281cf1237b94e7"
                                        "22d3d3d1a6a11177841602e8e9eb15f797949151"
                                        "0618c5188f2fb2c48cdc7d8e27c17276befdc813"
                                        "c0e90ba269dcbcfc7075512935c6b8fab1ead4d4"]])

  (spec->urls ["rijksmuseum" "6b4118e96e1b25b376c46086c4c0e00898ff13e2"])

  (go (prn (<! (get-specs example-card-specs))))

(go (-> (<! (get-json "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2"))
         prn))

  (go (-> (<! (get-json "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2")))
          prn)

  (go (-> (<! (apply get-json-bulk test-urls))
           prn))

  (go (prn (<! (go "aap"))))

  (go (let [chans (vec (map get-json test-urls))]
          (loop [i 0
                 res []]
            (if (= i (count chans))
              res
              (recur (inc i) (conj res (<! (chans i)))) ))))
)

;;(defn get-data [url]
;;  (xhr/send url
;;            (fn [e] (prn (.getResponseJson (.-target e))))))

;;(def xhr (net/xhr-connection))

;;(event/listen xhr goog.net.EventType.COMPLETE
;;              (fn [e] (prn (.getResponseJson (.-target e)))))

;;(net/transmit xhr "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2")

;;(get-data "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2")


