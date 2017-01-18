(ns app.progress)

(defn status
  "display the progress status"
  ([percent] (status percent ""))
  ([percent msg] (status percent msg 40))
  ([percent msg width]
    (print (str
      "\r"
      (format "%3.0f" (float (* percent 100)))
      "% ["
      (clojure.string/join ""
        (seq
          (for [i (range width)]
            (if (<= i (* (min percent 1.0) (/ width 1.0))) "=" " "))))
      "] "
      (format (str "%-" (- 80 width 8) "s") msg)))
    (flush)))
