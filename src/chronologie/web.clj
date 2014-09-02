(ns chronologie.web
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.resource :refer [wrap-resource]]
						[ring.middleware.file :refer [wrap-file]]
						[ring.middleware.file-info :refer [wrap-file-info]]
						[ring.util.response :refer [resource-response]]
            [environ.core :refer [env]]))
						
(def index-file
	(cond (env :index-file) (env :index-file)
				(= true (env :production)) "public/index.html"
				true "public/debug.html"))

(defn index [request]
	(resource-response index-file))
	
(def static
	(-> index
			(wrap-resource "/public")
			(wrap-file-info)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty #'static {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
