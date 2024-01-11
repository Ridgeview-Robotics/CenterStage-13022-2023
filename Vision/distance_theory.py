import cv2
import numpy as np

font = cv2.FONT_HERSHEY_SIMPLEX

focalPoint = 4
realHeight = 80.156     
sensorHeight = 2.02

irlHeight = (focalPoint * realHeight * imageHeight)/(objectHeight * sensorHeight)