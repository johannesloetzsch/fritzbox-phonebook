(ns fritzbox-phonebook.2csv-thunderbird
  (:require [clojure.string :refer [join]]))

(defn contact->thunderbird-csv [contact]
  (join ","
  #_["Vorname"
   "Nachname"
   "ignore1"
   "ignore2"
   "E-Mail"
   "ignore3"
   "Tel. dienstlich"
   "Tel. privat"
   "Fax"
   "Weitere NR"
   "Tel. Mobil"
   "Str."
   "Hausnr."
   "Stadt"
   "ignore4"
   "Plz"
   "ignore5"
   "ignore6"
   "ignore7"
   "ignore8"
   "ignore9"
   "ignore10"
   "ignore11"
   "ignore12"
   "ignore13"
   "Firma->NR"
   "ignoreOrganisation" "ignoreWebseite" "ignoreWebseite2"
   "ignoreGeburtsjahr" "ignoreGeburtsmonat" "ignoreGeburtstag"
   "ignoreBenutzerdef1" "ignoreBenutzerdef2" "ignoreBenutzerdef3" "ignoreBenutzerdef4"
   "Notizen" "unknown"]
  [(:firstname contact)
   (:lastname contact)
   "ignore1"
   "ignore2"
   ""  ;; Mail
   "ignore3"
   (first (:numbers-work contact))
   (if-let [home (first (:numbers-home contact))]
           home
           (nth (:numbers-work contact) 3 nil))
   (second (:numbers-work contact))
   (nth (:numbers-work contact) 2 nil)
   (if-let [mobile (first (:numbers-mobile contact))]
           mobile
           (nth (:numbers-work contact) 4 nil))
   ""  ;; Str
   ""  ;; Nr
   ""  ;; Stadt
   ""
   ""  ;; Plz
   "ignore5"
   "ignore6"
   "ignore7"
   "ignore8"
   "ignore9"
   "ignore10"
   "ignore11"
   "ignore12"
   "ignore13"
   (:company contact)
   "ignoreOrganisation" "ignoreWebseite" "ignoreWebseite2"
   "ignoreGeburtsjahr" "ignoreGeburtsmonat" "ignoreGeburtstag"
   "ignoreBenutzerdef1" "ignoreBenutzerdef2" "ignoreBenutzerdef3" "ignoreBenutzerdef4"
   "" "unknown"]))

(defn contacts->thunderbird-csv [contacts]
  (join "\r\n" (map contact->thunderbird-csv (remove #(and (empty? (:firstname %)) (empty? (:lastname %)))
                                                     contacts))))
