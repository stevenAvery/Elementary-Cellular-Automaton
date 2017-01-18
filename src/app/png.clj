(ns app.png
  (:require [app.progress :as progress])
  (:import (java.awt.image BufferedImage)
           (javax.imageio ImageIO)
           (java.io File)
           (java.awt Color)))

(def imageType "png")
(def backgroundColour (Color. 255 255 255))
(def gridColour       (Color. 196 196 196))
(def cellColour       (Color. 0   0   0))

(defn drawGrid
  "draws the grid for the output image"
  [g imageSize cellSize]
  ;; vertical lines
  (doseq [x (range 0 (imageSize :width) cellSize)]
    (.drawLine g x 0 x (imageSize :height)))
  ;; horizontal lines
  (doseq [y (range 0 (imageSize :height) cellSize)]
    (.drawLine g 0 y (imageSize :width) y)))

(defn drawCells
  "draws the cells for the output image"
  ([g gridCells gridSize imageSize cellSize verbose?]
    (drawCells g gridCells gridSize imageSize cellSize verbose? 0 0))
  ([g gridCells gridSize imageSize cellSize verbose? cell prevy]
    ;(when (> (quot cell (gridSize :width)) y)
    ;  (progress/status (/ cell (count gridCells)) "Outputting eca results to png"))

    (when (< cell (count gridCells))
      (let [x (mod cell (gridSize :width)) y (quot cell (gridSize :width))]
        ;; output progress when we start a new line
        (when (and verbose? (> y prevy))
          (progress/status (/ y (dec (gridSize :height)))
            (str "image cells [line " y " of " (dec (gridSize :height)) "]")))

        ;; output the cell when gridcell is true
        (when (== (nth gridCells cell) 1)
          (.fillRect g
            (inc (* x cellSize)) (inc (* y cellSize))
            (dec cellSize) (dec cellSize)))

        ;; output the next cell
        (recur g gridCells gridSize imageSize cellSize verbose? (inc cell) y)))))


(defn outputPNG
  "saves the gridCells to a png image"
  [gridCells imageName gridSize cellSize verbose?]
  (let [imageSize {:width  (inc (* (gridSize :width)  cellSize))
                   :height (inc (* (gridSize :height) cellSize))}]
    ;; create java buffered imaage
    (def bi (BufferedImage. (imageSize :width) (imageSize :height) BufferedImage/TYPE_INT_RGB))
    (def g (.createGraphics bi))

    ;; draw background
    (when verbose?
      (progress/status 0.01 "image background"))
    (.setColor g backgroundColour)
    (.fillRect g 0 0 (imageSize :width) (imageSize :height))

    ;; draw grid
    (when verbose?
      (progress/status 0.01 "image grid"))
    (.setColor g gridColour)
    (drawGrid g imageSize cellSize)

    ;; draw cells
    (.setColor g cellColour)
    (drawCells g gridCells gridSize imageSize cellSize verbose?)

    ;; save the image
    (ImageIO/write bi imageType (File. imageName))))
