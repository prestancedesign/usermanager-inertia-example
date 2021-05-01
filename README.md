# Single page application in Clojure Ring, Reitit + Reagent/Inertia.js

Example to demonstrate the use of [Inertia-Clojure](https://github.com/prestancedesign/inertia-clojure) + [Inertia.js](https://inertiajs.com/).

I started from this back-end web application [usermanager-reitit-integrant-example](https://github.com/prestancedesign/usermanager-reitit-integrant-example) and a few basic modifications are enough to transform it to a Single Page Application.

You can check the commit diff of what it took to change the app to SPA (server side): [commit/a4e3ea70ca9b9aa3b99e6512edc3a8142bb8b005](https://github.com/prestancedesign/reagent-inertia-reitit-integrant-fullstack/commit/a4e3ea70ca9b9aa3b99e6512edc3a8142bb8b005?branch=a4e3ea70ca9b9aa3b99e6512edc3a8142bb8b005&diff=split)

For convenience, the repository already contains the bundled front-end [resources/public/assets/js/app.js](resources/public/assets/js), so it can be tried quickly.
For those who want to play with the client side, just go to the [front](front/) directory and run `npm i && npx shadow-cljs watch app`.

All Reagent components are located in this [file](front/src/reagent/inertia.cljs).

## Launch the demo

Clone the repo, `cd` into it, then follow below to _Run the Application_.

You can launch the application by directly calling the namespace which contains the `-main` function in an terminal.

### Run the Application

    $ clj -M -m usermanager.system

or more conveniently, using an alias configured in `deps.edn` file.

    $ clj -M:run

Now acces the app at: [http://localhost:3000/](http://localhost:3000/).


## License & Copyright

Copyright (c) 2021 Prestance / Michaël SALIHI.

Distributed under the Apache Source License 2.0.
