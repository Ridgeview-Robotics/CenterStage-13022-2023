package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name= "Drone Servo Finder")
public class DroneServoPositionFinder extends OpMode {

    DroneHolderServo droneHolderServo;

    @Override
    public void init() {
        droneHolderServo = new DroneHolderServo(hardwareMap);

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0){
            droneHolderServo.setDroneHolderPosition(droneHolderServo.getDroneServoPos() + 0.001);
        }
        if(gamepad1.left_trigger > 0){
            droneHolderServo.setDroneHolderPosition(droneHolderServo.getDroneServoPos() - 0.001);
        }
        if(gamepad1.a){
            droneHolderServo.setDroneHolderPosition(0);
        }
        if (gamepad1.y) {
            droneHolderServo.setDroneHolderPosition(1);
        }

        telemetry.addLine("Use the right trigger for positive gain");
        telemetry.addLine("Use left trigger for negative gain");
        telemetry.addLine("Use A for 0 and Y for 1");
        telemetry.addLine("Drone Servo Position: " + droneHolderServo.getDroneServoPos());
    }
}
