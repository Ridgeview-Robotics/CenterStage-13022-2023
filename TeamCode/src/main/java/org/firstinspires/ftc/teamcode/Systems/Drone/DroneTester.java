package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Drone Tester")
public class DroneTester extends OpMode {

    Flywheels flywheels;
    DroneHolderServo droneServo;

    @Override
    public void init() {
        flywheels = new Flywheels(hardwareMap);
        droneServo = new DroneHolderServo(hardwareMap);

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        double motorPower = gamepad1.right_trigger;

        flywheels.setFlywheelsPower(motorPower);

        if(gamepad1.a){
            droneServo.setDroneServoTaut();
        }

        if(gamepad1.b){
            droneServo.setDroneServoRelease();
        }

        telemetry.addLine("Flywheel Input Power: " + flywheels.getPower());
        telemetry.addLine("Theoretical Flywheel Speed: " + (gamepad1.right_trigger * 6000));
    }
}
