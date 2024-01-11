import cv2
import numpy as np

frame = cv2.VideoCapture(0)


while(frame.isOpened()):

    ret, cap = frame.read()

    def find_red_cube_distance(image_path):
        # Load the image
        img = cv2.imread(image_path)
    
        # Convert BGR to HSV
        hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

        # Define the range of red color in HSV
        lower_red = np.array([0, 100, 100])
        upper_red = np.array([10, 255, 255])
    
        # Threshold the image to get only red colors
        mask = cv2.inRange(hsv, lower_red, upper_red)

        # Find contours in the mask
        contours, _ = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        # Filter out small contours
        contours = [cnt for cnt in contours if cv2.contourArea(cnt) > 100]

        if contours:
            # Assuming the largest contour is the red cube
            largest_contour = max(contours, key=cv2.contourArea)
        
            # Calculate the distance based on the contour size or other techniques
        
            # For simplicity, let's just print the area of the contour
            distance = cv2.contourArea(largest_contour)
            print("Distance to red cube:", distance)

        else:
            print("Red cube not found")

        cv2.imshow("Cone Color Detection", cap)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

cv2.release()
cv2.destroyAllWindows()