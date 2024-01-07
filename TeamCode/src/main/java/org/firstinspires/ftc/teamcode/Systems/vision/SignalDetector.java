package org.firstinspires.ftc.teamcode.subsystems.Vision;

import android.util.Log;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystems.Vision.SignalDetectorPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import java.util.ArrayList;


public class SignalDetector {

    private OpenCvWebcam webcam;
    public SignalDetectorPipeline pipeline;

    private int FOUND_TAG;

    public SignalDetector(HardwareMap hardwareMap, Telemetry telemetry) {

        // Initialize webcam
        int cameraMonitorViewId = hardwareMap.appContext.getResources( ).getIdentifier( "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam( hardwareMap.get( WebcamName.class, "webcam1"), cameraMonitorViewId);
        pipeline = new SignalDetectorPipeline(telemetry);
        webcam.setPipeline(pipeline);
        openCameraDevice();
    }

    public int getFoundTag() {
        return FOUND_TAG;
    }

    // Opens video stream
    public void openCameraDevice() {

        webcam.openCameraDeviceAsync( new OpenCvCamera.AsyncCameraOpenListener( ) {
            @Override
            public void onOpened() {

                /*
                1080p: 1920×1080
                720p: 1280×720
                480p: 864×480
                360p: 640×360
                 */

                webcam.startStreaming( 640, 360, OpenCvCameraRotation.UPRIGHT );
            }

            @Override
            public void onError( int errorCode ) {
                //This will be called if the camera could not be opened
                Log.e( "CAMERA_DEVICE", "Camera could not be opened. Error code: " + errorCode );
            }
        } );
    }

    // Run pipeline and identify found tags
    public void runDetection() {
        ArrayList<AprilTagDetection> currentDetections = pipeline.getLatestDetections();

        if (currentDetections.size() != 0) {
            for (AprilTagDetection tag : currentDetections) {
                FOUND_TAG = tag.id;
            }
        }
        else {
            FOUND_TAG = 2000;
        }
    }

    // Returns Parking Spot Number
    public int getParkingSpot() {
        int foundTag = getFoundTag();
        if (foundTag <= 2) {
            return foundTag + 1;
        }
        else if (foundTag == 2000){
            return 2;
        }
        else {
            return foundTag - 2;
        }
    }

    public int getAllianceColor() { // 0 = Blue, 1 = Red
        int foundTag = getFoundTag();
        if (foundTag <= 2) {
            return 1;
        }
        if (foundTag <= 5) {
            return 0;
        }
        return 2;

    }
    public void stopCamera( ) {
        webcam.stopStreaming( );
    }
}

