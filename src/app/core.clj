(ns app.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]
            [app.eca :as eca]
            [app.png :as png])
  (:gen-class))

;; default constants
(def ^:const defaultIterations 32)
(def ^:const defaultImageName  "res/output.png")
(def ^:const defaultCellSize   4)

(def cli-options
  [["-i" "--iterations ITERATIONS" "Number of iterations to compute"
     :default defaultIterations
     :parse-fn #(Integer/parseInt %)
     :validate [#(> % 0) "Must be a number greater then 0"]]
   ["-o" "--imageName IMAGE_NAME" "Name of the output image"
     :default defaultImageName]
   ["-c" "--cellSize CELL_SIZE" "The size of the cells in the output image"
     :default defaultCellSize
     :parse-fn #(Integer/parseInt %)
     :validate [#(> % 1) "Must be a number greater then 1"]]
   ["-p" "--png" "Boolean to output to png"]
   ["-t" "--terminal" "Boolean to output to terminal"]
   ["-v" "--verbose" "Boolean to output additional progress information"]
   ["-h" "--help"]])

(defn usage
  "the intended command-line usage for the program"
  [options-summary]
  (->> ["Computes elementary cellular automaton, and outputs to console or png."
        ""
        "Usage: lein run rule [options]"
        ""
        "Options:"
        options-summary
        ""
        "Arguments:"
        "    rule    Rule for elementary cellular automaton"
        "            Must be a number between 0 and 255"
        ""]
       (string/join \newline)))

(defn error-msg
  "output an error message to the user"
  [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit
  "exit the program if there is an error parsing input args"
  [status msg]
  (println msg)
  (System/exit status))

(defn toInt
  "Convert a string to int"
  [s]
  (if-let [d (re-find #"-?\d+" s)] (Integer/parseInt d)
  -1))

(defn -main [& args]
 (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;; handle help and error conditions
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))

      ;; parse the rule argument
      (def rule
        (if (and (>= (toInt (first arguments)) 0)
                 (<= (toInt (first arguments)) 255))
          (toInt (first arguments))
          (exit 1 (error-msg ["Unable to convert Rule to int"]))))

      ;; get all other options from tools.cli
      (def iterations (options :iterations))
      (def imageName  (options :imageName))
      (def png?       (options :png))
      (def terminal?  (options :terminal))
      (def verbose?   (options :verbose))
      (def cellSize   (options :cellSize))

      ;; calculate the elementary cellular automaton
      ;; calculate the final gridsize (each step the grid will expand by one on each end)
      (def gridSize {:width (dec (* iterations 2)) :height iterations})
      ;; generate the first row of cells
      (def cells (eca/zeroBookendLength '(1) (gridSize :width)))
      ;; get the final grid of cells
      (def gridCells (eca/run cells (eca/decToRule rule) iterations verbose?))

      ;; output the result of the elementary cellular automaton to the terminal
      (when terminal?
        (eca/printCells gridCells gridSize ))

      ;; save the result of the elementary cellular automaton as a PNG
      (when verbose? (println ""))
      (when png?
        (png/outputPNG gridCells imageName gridSize cellSize verbose?))

      (print "")))
