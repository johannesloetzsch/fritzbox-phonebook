(ns fritzbox-phonebook.lib.import-agfeo
  (:require [clojure.data.csv :refer [read-csv]]
            [clojure.string :refer [join]]))

(defn import-agfeo [filename-csv]
  (let [csv (read-csv (slurp filename-csv :encoding "ISO-8859-1")
                      :separator \;)
        header (first csv)]
       (assert (= (take 6 header)
                  ["Nachname" "Vorname" "2. Vorname" "contact_title" "Firma" "Abteilung"]))
       (assert (= (nth header 6) "contact_birthday"))
       (assert (= (nth header 7) "Rufnummer (privat)"))
       (assert (= (nth header 8) "Mobiltelefon"))
       (assert (= (drop 9 header) ["Rufnummer (geschäftlich)" "fon_business_1" "fon_business_2" "fon_business_3" "fon_business_4" "fon_business_5" "fon_business_6" "fon_business_7" "fon_business_8"]))
       (map #(hash-map :lastname (nth % 0)
                       :firstname (nth % 1)
                       :middlename (nth % 2)
                       :title (nth % 3)
                       :company (nth % 4)
                       :department (nth % 5)
                       :display-name (join " " (remove empty? (take 8 %)))
                       :numbers-home (remove empty? [(nth % 7)])
                       :numbers-mobile (remove empty? [(nth % 8)])
                       :numbers-work (remove empty? (drop 9 %)))
            (rest csv))))

