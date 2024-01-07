import cv2
import numpy as np

# Band-aid fix for callback method needed as a parameter in the trackbar function
def nothing(x):
    pass

# Init Webcam
cap = cv2.VideoCapture(0)

# Init Window
cv2.namedWindow("HSV Tuning")

# Trackbars for changing channel levels (H,S,V) -- Low and High 
cv2.createTrackbar("L - H", "HSV Tuning", 0, 179, nothing)
cv2.createTrackbar("L - S", "HSV Tuning", 0, 255, nothing)
cv2.createTrackbar("L - V", "HSV Tuning", 0, 255, nothing)
cv2.createTrackbar("U - H", "HSV Tuning", 179, 179, nothing)
cv2.createTrackbar("U - S", "HSV Tuning", 255, 255, nothing)
cv2.createTrackbar("U - V", "HSV Tuning", 255, 255, nothing)
 
while True:
    
    # Grab webcam frame
    ret, frame = cap.read()
    if not ret:
        break
    
    # Flip the frame horizontally 
    frame = cv2.flip( frame, 1 ) 
    
    # Convert the from BGR color space to HSV
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    
    # Read value of trackbars as they are changed
    l_h = cv2.getTrackbarPos("L - H", "HSV Tuning")
    l_s = cv2.getTrackbarPos("L - S", "HSV Tuning")
    l_v = cv2.getTrackbarPos("L - V", "HSV Tuning")
    u_h = cv2.getTrackbarPos("U - H", "HSV Tuning")
    u_s = cv2.getTrackbarPos("U - S", "HSV Tuning")
    u_v = cv2.getTrackbarPos("U - V", "HSV Tuning")
 
    # Store Tuned HSv values in arrays 
    lower_range = np.array([l_h, l_s, l_v])
    upper_range = np.array([u_h, u_s, u_v])
    
    # Filter the image based on trackbar values
    mask = cv2.inRange(hsv, lower_range, upper_range)
 
    # Color stuff (Not sure this is needed)
    res = cv2.bitwise_and(frame, frame, mask=mask)
    mask_3 = cv2.cvtColor(mask, cv2.COLOR_GRAY2BGR)
    
    # Stack Three Views 
    stacked = np.hstack((mask_3,frame,res))
    
    # Show this stacked frame at 40% of the size
    cv2.imshow('HSV Tuning',cv2.resize(stacked,None,fx=0.4,fy=0.4))
    
    # If ESC is pressed then exit the program
    key = cv2.waitKey(1)
    if key == 27:
        break
    
    # If `s` is pressed prints the array holding the tuned HSV values
    if key == ord('s'):
        
        thearray = [[l_h,l_s,l_v],[u_h, u_s, u_v]]

        
        print("Upper: " + str(u_h) + ", " + str(u_s) + ", " + str(u_v))
        print("Lower: " + str(l_h) + ", " + str(l_s) + ", " + str(l_v))
        
        break
    
# Release the camera & destroy the windows  
cap.release()
cv2.destroyAllWindows()