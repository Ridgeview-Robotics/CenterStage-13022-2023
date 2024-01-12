import cv2
import numpy as np

# Font to write text overlay
font = cv2.FONT_HERSHEY_SIMPLEX

# Create lists that holds the color thresholds
# Blue
hsvMinBlue = (101,150,130)
hsvMaxBlue = (123,255,215)

# Red 
hsvMinRed = (126,121,35)
hsvMaxRed = (179,255,255)

# Green 
hsvMinGreen = (51,70,41)
hsvMaxGreen = (87,255,255)


frame = cv2.VideoCapture(0)

# Logi720 fl=4.0 sh= 2.02
# Logi1080 fl= 3.67 sh= 

fl = 4.0 #focal length in mm 
rh = 100.2 #real height in mm
sh = 2.02 #sensor height in mm
ih = 720 #image height in px

def disteq(oh):
    return str((fl*rh*ih)/(oh*sh))


while(frame.isOpened()):

    
    ret, cap = frame.read()

    # Convert to HSV
    hsv = cv2.cvtColor(cap, cv2.COLOR_BGR2HSV)

    # Apply HSV thresholds 
    redMask = cv2.inRange(hsv, hsvMinRed, hsvMaxRed)
    blueMask = cv2.inRange(hsv, hsvMinBlue, hsvMaxBlue)
    greenMask = cv2.inRange(hsv, hsvMinGreen, hsvMaxGreen)
    
    
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

    
    
    for pic, redContour in enumerate(redContours):
        area = cv2.contourArea(redContour)
        if(area > 300):
            x, y, w, h = cv2.boundingRect(redContour)
            cap = cv2.rectangle(cap, (x, y), 
                                       (x + w, y + h), 
                                       (0, 0, 255), 2
                                       )
            
              
            cv2.putText(cap, "red" + disteq(h), (x, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 1.0,
                        (0, 0, 255))   

            print(h)  
            print: disteq(h)

    
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
   
    

    # Show image
    cv2.imshow("Cone Color Detection", cap)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cv2.release()
cv2.destroyAllWindows()