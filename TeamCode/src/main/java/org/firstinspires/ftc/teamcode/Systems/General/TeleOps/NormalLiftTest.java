package org.firstinspires.ftc.teamcode.Systems.General.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;

@TeleOp(name = "Lift Test with Joysticks")
public class NormalLiftTest extends OpMode {

    CombineLiftC lift;

    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);

        lift.yaw.runUsingEncoderMode();
        lift.outboard.runUsingEncoderMode();
    }

    @Override
    public void loop() {
        double outboardPower = gamepad1.left_stick_y;
        double yawPower = gamepad1.right_stick_x;

        lift.setYawPower(yawPower);
        lift.setOutboardPower(outboardPower);

        telemetry.addLine("Yaw Power: " + yawPower);
        telemetry.addLine("Outboard Power: " + outboardPower);
    }
}
