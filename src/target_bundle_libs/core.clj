(ns target-bundle-libs.core
  (:require [cheshire.core :as json])
  (:refer-clojure :exclude [run!])
  (:gen-class))

(defn read-package-json [package-json]
  (-> package-json slurp json/decode))

(defn package-json->deps-cljs [pj]
  (let [npm-deps (get pj "dependencies")]
    {:npm-deps npm-deps}))

(defn run!
  [package-json deps-cljs]
  (println "Reading" package-json)
  (let [pj (read-package-json package-json)]
    (println "Writing" deps-cljs)
    (spit deps-cljs (package-json->deps-cljs pj))))

(defn -main [& args]
  (run! "./package.json" "./src/deps.cljs"))
