package org.firstinspires.ftc.teamcode.subsystems.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Vision.SignalDetector;

/*
    Tests the Signal Detector April Tag Pipeline
 */
@Autonomous(name = "Signal Detection")
@Disabled
public class SignalDetectorTest extends LinearOpMode
{
    // Declare Vision system object
    private SignalDetector signalDetector;

    @Override
    public void runOpMode() {

        // Initialize vision object
        signalDetector = new SignalDetector(hardwareMap, telemetry);

        // Alert driver init finished
        telemetry.addLine("Ready for Start!");
        telemetry.update();

        // Wait for opMode start
        waitForStart();

        if (isStopRequested()) return;

        ///////////////GAME START////////////////////////////////

        while (opModeIsActive()) {
            // Run Detection Pipeline
            signalDetector.runDetection();

            int parkingSpot = signalDetector.getParkingSpot();
            int allianceColor = signalDetector.getAllianceColor();

            if (allianceColor == 0) {
                telemetry.addLine("Blue");
                telemetry.addLine("Parking Spot: " + parkingSpot);
            }
            else if (allianceColor == 1){
                telemetry.addLine("Red");
                telemetry.addLine("Parking Spot: " + parkingSpot);
            }
            else {
                telemetry.addLine("No Tag Found :(");
                telemetry.addLine("Parking Spot: " + parkingSpot);
            }

            telemetry.addLine("Found Tag: " + signalDetector.getFoundTag());

            telemetry.update();
        }
    }
}