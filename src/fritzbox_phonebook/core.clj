(ns fritzbox-phonebook.core
  (:require [fritzbox-phonebook.lib.import-agfeo :refer [import-agfeo]]
            [fritzbox-phonebook.2xml :refer [contacts->fritz-hiccup hiccup->xml]])
  (:gen-class))

(defn -main
  [& args]
  (let [filename (first args)
        list-of-contacts (import-agfeo filename)]
       (print (hiccup->xml (contacts->fritz-hiccup list-of-contacts)))))
