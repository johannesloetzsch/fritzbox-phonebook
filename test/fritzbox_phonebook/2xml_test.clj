(ns fritzbox-phonebook.2xml-test
  (:require [clojure.test :refer :all]
            [fritzbox-phonebook.2xml :refer [contact->fritz-hiccup contacts->fritz-hiccup hiccup->xml]]))

(def list-of-contacts [{:display-name "name" :numbers-home [1 2 3] :numbers-mobile [4 5 6] :numbers-work [7 8 9] :foo :bar}])

(deftest test-contact->fritz-hiccup
  (is (= [:contact
          [:person [:realName "name"]]
          [:telephony
           [:number {:id 0, :prio 0, :type "home"} 1]
           [:number {:id 1, :prio 1, :type "home"} 2]
           [:number {:id 2, :prio 2, :type "home"} 3]
           [:number {:id 3, :prio 3, :type "mobile"} 4]
           [:number {:id 4, :prio 4, :type "mobile"} 5]
           [:number {:id 5, :prio 5, :type "mobile"} 6]
           [:number {:id 6, :prio 6, :type "work"} 7]
           [:number {:id 7, :prio 7, :type "work"} 8]
           [:number {:id 8, :prio 8, :type "work"} 9]]
          [:other [:foo :bar]]]
         (contact->fritz-hiccup (first list-of-contacts)))))

(deftest test-contact->fritz-xml
  (is (=
"<?xml version=\"1.0\" encoding=\"UTF-8\"?><phonebooks>
  <phonebook name=\"Telefonbuch\">
    <contact>
      <person>
        <realName>name</realName>
      </person>
      <telephony/>
      <number id=\"0\" prio=\"0\" type=\"home\">1</number>
      <number id=\"1\" prio=\"1\" type=\"home\">2</number>
      <number id=\"2\" prio=\"2\" type=\"home\">3</number>
      <number id=\"3\" prio=\"3\" type=\"mobile\">4</number>
      <number id=\"4\" prio=\"4\" type=\"mobile\">5</number>
      <number id=\"5\" prio=\"5\" type=\"mobile\">6</number>
      <number id=\"6\" prio=\"6\" type=\"work\">7</number>
      <number id=\"7\" prio=\"7\" type=\"work\">8</number>
      <number id=\"8\" prio=\"8\" type=\"work\">9</number>
      <other/>
      <foo>
        <bar/>
      </foo>
    </contact>
  </phonebook>
</phonebooks>
"
         (hiccup->xml (contacts->fritz-hiccup list-of-contacts)))))
