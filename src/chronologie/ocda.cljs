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
  (let [out (chan 1)]
    (go (let [chans (map get-json urls)]
          (loop [i 0 ret []]
            (if (= (count ret) (count chans)))
            (>! out ret)
            (let [[v _] (alts! chans)]
              (recur (inc i) (conj ret v))))))
    out))

(def test-urls ["http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2"
                "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2"])

(comment

(go (-> (<! (get-json "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2"))
         prn))

  (go (-> (<! (get-json "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2")))
          prn)

  (go (-> (<! (apply get-json-bulk test-urls))
           prn))

  (go (prn (<! (go "aap"))))

  (go (let [chans (map get-json test-urls)]
        (loop [i 0 ret []]
          (if (= i (count chans)))
          ret
          (let [[v _] (alts! chans)]
            (recur (inc i) (conj ret v))))))

)

;;(defn get-data [url]
;;  (xhr/send url
;;            (fn [e] (prn (.getResponseJson (.-target e))))))

;;(def xhr (net/xhr-connection))

;;(event/listen xhr goog.net.EventType.COMPLETE
;;              (fn [e] (prn (.getResponseJson (.-target e)))))

;;(net/transmit xhr "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2")

;;(get-data "http://api.opencultuurdata.nl/v0/rijksmuseum/6b4118e96e1b25b376c46086c4c0e00898ff13e2")


