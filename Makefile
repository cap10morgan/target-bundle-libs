.PHONY: install deploy test

target-bundle-libs.jar: deps.edn pom.xml src/**/*
	clojure -A:uberjar

pom.xml: deps.edn
	clojure -Spom

install: target-bundle-libs.jar pom.xml
	clojure -A:install

deploy: target-bundle-libs.jar pom.xml
	clojure -A:deploy

test:
	clojure -A:test:runner