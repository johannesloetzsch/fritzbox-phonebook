(ns fritzbox-phonebook.lib.hiccup)

(defn ->hiccup
  "use it like: (->hiccup (parse-str (slurp filename)))"
  [e]
  (if-not (map? e)
          e
          (into []
                (concat [(:tag e)]
                        (if-not (empty? (:attrs e))
                                [(:attrs e)])
                        (map ->hiccup (:content e))))))
