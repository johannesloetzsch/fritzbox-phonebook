(ns fritzbox-phonebook.2xml
  (:require [clojure.data.xml :refer [indent-str sexp-as-element]]))

(defn contact->fritz-hiccup [contact]
  [:contact
    [:person [:realName (:display-name contact)] #_[:imageURL]]
    (cons :telephony
      #_{:nid "4"}
      (->> (map (fn [kw] (map #(vector kw %) (kw contact)))
                [:numbers-home :numbers-mobile :numbers-work])
           (apply concat)
           (map-indexed (fn [i v] [i v]))
           (map #(let [[i [type_original number]] %
                       type_fritz (type_original {:numbers-home "home"
                                                  :numbers-mobile "mobile"
                                                  :numbers-work "work"})]
                      [:number {:id i :prio i :type type_fritz #_#_:vanity ""} number]))))
   (cons :other (as-> contact x
                      (into {} (remove #(= "" (val %)) x))
                      (dissoc x :display-name :numbers-home :numbers-mobile :numbers-work)
                      (map #(if (and (sequential? (val %)) (not (string? (val %))))
                                (cons (key %) (val %))
                                %)
                           x)))])

(defn contacts->fritz-hiccup [contacts]
  [:phonebooks
    [:phonebook
    {:name "Telefonbuch"}
    (map contact->fritz-hiccup contacts)]])

(defn hiccup->xml [hiccup]
  (indent-str (sexp-as-element hiccup)))
