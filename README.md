# target-bundle-libs

A helper to generate `src/deps.cljs` files from `package.json` files. Allows
simple, consistent ClojureScript library development with `:target :bundle`.

The core problem it was created to solve is that out of the box, you can't depend
on foreign JS / NPM libraries in CLJS libraries with `:target :bundle`. Everything
will appear to work until you try to consume the CLJS library in an application.
It won't be able to find the transitive JS libraries. This solves that.

**Note:** You *must* set the following compiler options in all apps that consume
libraries using this tool:

```clojure
{:install-deps true
 :npm-deps {}} ; :npm-deps set to empty map is only needed if you don't already have it set
```

Unfortunately the compiler (as of 1.10.773) crashes if `:install-deps` is true
but `:npm-deps` isn't set. Hopefully that will be fixed in the future.

## Usage

Create a `:js-deps` alias in your `deps.edn`:

```clojure
{:aliases
 {:js-deps
  {:extra-deps {com.timetraveltoaster/target-bundle-libs {:mvn/version "RELEASE"}}
   :main-opts ["-m" "target-bundle-libs.core"]}}}
```

Then, wherever your build & package pipeline is defined (e.g. a `Makefile`), add this:

`clojure -A:js-deps`

...before the step that packages your JAR file. This will generate a `src/deps.cljs`
file from your `package.json` (it intentionally does not read `package-lock.json` nor
`yarn.lock` because libraries should be compatible with every dependency version they
specify in their `package.json`'s semver ranges).

When applications consume your library now (assuming you added the `:install-deps true`
and `:npm-deps {}` entries to your compiler options), they will see the `deps.cljs` and
install and use your transitive JS deps.

## Background

For a more in-depth discussion of why this is helpful, see my [blog post about
ClojureScript libraries with target-bundle](https://timetraveltoaster.com/clojurescript-libraries-in-2020/).

## Development

Run the project's tests:

    $ clojure -A:test:runner

Build an uberjar:

    $ clojure -A:uberjar

Run that uberjar:

    $ java -jar target-bundle-libs.jar

### Bugs

1. Currently this likely assumes too much that should be more flexible / configurable (e.g. file paths).

## License

Copyright © 2020 Wes Morgan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
