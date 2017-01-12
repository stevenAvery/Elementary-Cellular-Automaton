(ns app.png
  (:import (java.awt.image BufferedImage)
           (javax.imageio ImageIO)
           (java.io File)
           (java.awt Color)))

(def imageType "png")
(def backgroundColour (Color. 255 255 255))
(def gridColour       (Color. 196 196 196))
(def cellColour       (Color. 0   0   0))

(defn drawGrid
  "Draws the grid for the output image"
  [g imageSize cellSize]
  ;; vertical lines
  (doseq [x (range 0 (imageSize :width) cellSize)]
    (.drawLine g x 0 x (imageSize :height)))
  ;; horizontal lines
  (doseq [y (range 0 (imageSize :height) cellSize)]
    (.drawLine g 0 y (imageSize :width) y)))

(defn drawCells
  "Draws the cells for the output image"
  [g gridCells gridSize imageSize cellSize]
  (doseq [cell (range (count gridCells))
          :when (== (nth gridCells cell) 1)
          :let [x (mod cell (gridSize :width)) y (quot cell (gridSize :width))]]
    (.fillRect g
      (inc (* x cellSize)) (inc (* y cellSize))
      (dec cellSize) (dec cellSize))))

(defn outputPNG
  "Saves the gridCells to a png image"
  [gridCells imageName gridSize cellSize]
  (let [imageSize {:width  (inc (* (gridSize :width)  cellSize))
                   :height (inc (* (gridSize :height) cellSize))}]
    ;; create java buffered imaage
    (def bi (BufferedImage. (imageSize :width) (imageSize :height) BufferedImage/TYPE_INT_RGB))
    (def g (.createGraphics bi))

    ;; draw background
    (.setColor g backgroundColour)
    (.fillRect g 0 0 (imageSize :width) (imageSize :height))
    ;; draw grid
    (.setColor g gridColour)
    (drawGrid g imageSize cellSize)
    ;; draw cells
    (.setColor g cellColour)
    (drawCells g gridCells gridSize imageSize cellSize)

    ;; save the image
    (ImageIO/write bi imageType (File. imageName))))
