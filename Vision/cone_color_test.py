import cv2 
import numpy as np

# Font to write text overlay
font = cv2.FONT_HERSHEY_SIMPLEX

# Create lists that holds the color thresholds
# Blue
hsvMinBlue = (0,50,255)
hsvMaxBlue = (109,255,255)

# Red 
hsvMinRed = (0,117,186)
hsvMaxRed = (179,255,255)

# Green 
hsvMinGreen = (51,70,41)
hsvMaxGreen = (87,255,255)
"""
# Black
hsvMinBlack = (74, 0, 0)
hsvMaxBlack = (143, 109, 103)

# White
hsvMinWhite = (55, 0, 0)
hsvMaxWhite = (179, 24, 255)
"""
# Read Camera Frames 
# 0 -- Laptop Cam
# 1 -- USB Cam
#frame = cv2.VideoCapture('/dev/video2')
frame = cv2.VideoCapture(0)

while(frame.isOpened()):
    
    ret, cap = frame.read()

    # Convert to HSV
    hsv = cv2.cvtColor(cap, cv2.COLOR_BGR2HSV)

    # Apply HSV thresholds 
    redMask = cv2.inRange(hsv, hsvMinRed, hsvMaxRed)
    blueMask = cv2.inRange(hsv, hsvMinBlue, hsvMaxBlue)
    greenMask = cv2.inRange(hsv, hsvMinGreen, hsvMaxGreen)
    """blackMask = cv2.inRange(hsv, hsvMinBlack, hsvMaxBlack)
    whiteMask = cv2.inRange(hsv, hsvMinWhite, hsvMaxWhite)"""
    
     # Creating contour to track red color
    redContours, redHierarchy = cv2.findContours(redMask,
                                           cv2.RETR_TREE,
                                           cv2.CHAIN_APPROX_SIMPLE)

    blueContours, blueHierarchy = cv2.findContours(blueMask,
                                            cv2.RETR_TREE,
                                            cv2.CHAIN_APPROX_SIMPLE)

    greenContours, greenHierarchy = cv2.findContours(greenMask,
                                            cv2.RETR_TREE,
                                            cv2.CHAIN_APPROX_SIMPLE)

    """blackContours, blackHierarchy = cv2.findContours(blackMask,
                                            cv2.RETR_TREE,
                                            cv2.CHAIN_APPROX_SIMPLE)

    whiteContours, whiteHierarchy = cv2.findContours(whiteMask,
                                            cv2.RETR_TREE,
                                            cv2.CHAIN_APPROX_SIMPLE)"""
    
    for pic, redContour in enumerate(redContours):
        area = cv2.contourArea(redContour)
        if(area > 300):
            x, y, w, h = cv2.boundingRect(redContour)
            cap = cv2.rectangle(cap, (x, y), 
                                       (x + w, y + h), 
                                       (0, 0, 255), 2)
              
            cv2.putText(cap, "Red", (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (0, 0, 255))     

    
    for pic, blueContour in enumerate(blueContours):
        area = cv2.contourArea(blueContour)
        if(area > 300):
            x, y, w, h = cv2.boundingRect(blueContour)
            cap = cv2.rectangle(cap, (x, y), 
                                       (x + w, y + h), 
                                       (0, 0, 255), 2)
              
            cv2.putText(cap, "Blue", (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (0, 0, 255))   
    

    for pic, greenContour in enumerate(greenContours):
        area = cv2.contourArea(greenContour)
        if(area > 300):
            x, y, w, h = cv2.boundingRect(greenContour)
            cap = cv2.rectangle(cap, (x, y), 
                                       (x + w, y + h), 
                                       (0, 0, 255), 2)
              
            cv2.putText(cap, "Green", (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (0, 0, 255))   
   
    """for pic, blackContour in enumerate(blackContours):
        area = cv2.contourArea(blackContour)
        if(area > 300):
            x, y, w, h = cv2.boundingRect(blackContour)
            cap = cv2.rectangle(cap, (x, y), 
                                       (x + w, y + h), 
                                       (0, 0, 255), 2)
              
            cv2.putText(cap, "Black", (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (0, 0, 255))

    for pic, whiteContour in enumerate(whiteContours):
        area = cv2.contourArea(whiteContour)
        if(area > 300):
            x, y, w, h = cv2.boundingRect(whiteContour)
            cap = cv2.rectangle(cap, (x, y), 
                                       (x + w, y + h), 
                                       (0, 0, 255), 2)
              
            cv2.putText(cap, "White", (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (0, 0, 255))"""

    # Show image
    cv2.imshow("Cone Color Detection", cap)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cv2.release()
cv2.destroyAllWindows()

