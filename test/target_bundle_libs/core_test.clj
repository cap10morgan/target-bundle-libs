(ns target-bundle-libs.core-test
  (:require [clojure.test :refer :all]
            [target-bundle-libs.core :refer :all]
            [clojure.java.io :as io])
  (:refer-clojure :exclude [run!]))

(deftest read-package-json-test
  (testing "Reads JSON file into EDN"
    (is (= {"devDependencies" {"webpack" "^4.43.0"
                               "webpack-cli" "^3.3.12"}
            "dependencies" {"pad" "^3.2.0"}}
           (read-package-json (io/resource "test-package.json"))))))

(deftest package-json->deps-cljs-test
  (testing "Converts package.json derived EDN (just 'dependencies') to deps.cljs EDN"
    (is (= {:npm-deps {"pad" "^3.2.0"}}
           (-> "test-package.json"
               io/resource
               read-package-json
               package-json->deps-cljs)))))
