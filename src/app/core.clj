(ns app.core
  (:require [app.esc :as esc]
            [app.png :as png])
  (:gen-class))

;; usage rule iterations imageName cellSize
(def ^:const defaultRule       150)
(def ^:const defaultIterations 64)
(def ^:const defaultImageName  "output.png")
(def ^:const defaultCellSize   4)

(defn -main [& args]
  ;; if arg1 is a valid make it the rule, otherwise use default
  (def rule
    (if (and (>= (count args) 1)
             (>= (Integer. (first args)) 0)    ;; min rule
             (<= (Integer. (first args)) 255)) ;; max rule
      (Integer. (first args))
      defaultRule))

  ;; if arg2 is a valid make it the iterations, otherwise use default
  (def iterations
    (if (and (>= (count args) 2)
             (>= (Integer. (second args)) 0)) ;; min iterations
      (Integer. (second args))
      defaultIterations))

  ;; if arg3 is a valid make it the imageName, otherwise use default
  (def imageName
    (if (> (count args) 2)
      (nth args 2)
      defaultImageName))

  ;; if arg3 is a valid make it the cellSize, otherwise use default
  (def cellSize
    (if (and (> (count args) 3)
             (>= (Integer. (nth args 3)) 2)) ;; min cellSize
      (Integer. (nth args 3))
      defaultCellSize))

  (def gridSize {:width (dec (* iterations 2)) :height iterations})
  (def cells (esc/zeroBookendLength '(1) (gridSize :width)))
  (def gridCells (esc/run cells (esc/decToRule rule) iterations))

)
  ;;(println "From main:" args))
