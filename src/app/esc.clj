(ns app.esc)

(defn zeroBookend
  "bookend list with a zero"
  [toBookend]
  (concat [0] toBookend [0]))

(defn zeroBookendLength
  "pad a list with zeros to get a given alength"
  [inList inLength]
  (if (>= (count inList) inLength)
    inList
    (recur (zeroBookend inList) inLength)))

(defn decToBin
  "Convert a single decimal number to a binary list"
  [decNum]
  (map #(Character/getNumericValue %) (Integer/toBinaryString decNum)))

(defn binToDec
  "Convert a binary list to a single decimal number"
  [binList]
  (Integer/parseInt (apply str binList) 2))

(defn zeroPad
  "pad a list with zeros to get a given alength"
  [inList inLength]
  (if (= (count inList) inLength)
    inList
    (recur (concat [0] inList) inLength)))

(defn decToRule
  "convert a decimal number to valid rule"
  [decNum]
  (reverse (zeroPad (decToBin decNum) 8)))

(defn printCells
  "Draws the cells for the output image"
  [gridCells gridSize]
  (doseq [cell (range (count gridCells))]
    (when (== (mod cell (gridSize :width)) 0)
      (println ""))
    (if (== (nth gridCells cell) 1)
      (print "#")
      (print " "))))

(defn triples
  "return list of all triples in input ie: (1 2 3 4) returns ((1 2 3) (2 3 4))"
  [initList & triplesList]
  (let [triplesList (or triplesList '())
        newTriplesList (concat triplesList [(take 3 initList)])]
    (if (> (count initList) 3)
      (recur (drop 1 initList) newTriplesList)
      newTriplesList)))

(defn step
  "steps the cells with the given rule"
  [inCells inRule]
  (map #(nth inRule %)
    (map binToDec (triples (zeroBookend inCells)))))

(defn run
  "Steps the cells the given number of times"
  ;; if the user doesn't give the count of the current step assume zero
  ([inCells inRule stepCount]
    (run inCells inRule stepCount '()))
  ([inCells inRule stepCount gridCells]
    ;; if we have reached the end of the grid return
    (if (<= stepCount 1)
      (concat gridCells inCells)
      ;; otherwise step, and recursively keep running
      (recur (step inCells inRule) inRule (dec stepCount) (concat gridCells inCells)))))
