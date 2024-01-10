package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Drone Tester")
public class DroneTester extends OpMode {

    Flywheels flywheels;

    @Override
    public void init() {
        flywheels = new Flywheels(hardwareMap);



        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {

        flywheels.setFlywheelsPower(gamepad1.right_trigger);

        telemetry.addLine("Flywheel Input Power: " + flywheels.getPower());
        telemetry.addLine("Theoretical Flywheel Speed: " + (gamepad1.right_trigger * 6000));
    }
}
