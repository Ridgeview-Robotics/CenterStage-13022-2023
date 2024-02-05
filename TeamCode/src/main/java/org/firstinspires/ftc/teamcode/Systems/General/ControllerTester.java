package org.firstinspires.ftc.teamcode.Systems.General;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Controller Tester")
public class ControllerTester extends OpMode {
    @Override
    public void init() {
        telemetry.addLine("Ready for Start!");

        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addLine("Up: " + gamepad1.dpad_up);
        telemetry.addLine("Down: " + gamepad1.dpad_down);
        telemetry.addLine("Right: " + gamepad1.dpad_right);
        telemetry.addLine("Left: " + gamepad1.dpad_left);
        telemetry.addLine("A: " + gamepad1.a);
        telemetry.addLine("B: " + gamepad1.b);
        telemetry.addLine("X: " + gamepad1.x);
        telemetry.addLine("Y: " + gamepad1.y);
        telemetry.addLine("Options: " + gamepad1.options);
        telemetry.addLine("Share: " + gamepad1.share);
        telemetry.addLine("PS: " + gamepad1.ps);
        telemetry.addLine("Right Stick Button: " + gamepad1.right_stick_button);
        telemetry.addLine("Left Stick Button: " + gamepad1.left_stick_button);
        telemetry.addLine("Left Stick X: " + gamepad1.left_stick_x);
        telemetry.addLine("Left Stick Y: " + gamepad1.left_stick_y);
        telemetry.addLine("Right Stick X: " + gamepad1.right_stick_x);
        telemetry.addLine("Right Stick Y: " + gamepad1.right_stick_y);
        telemetry.addLine("Right Trigger: " + gamepad1.right_trigger);
        telemetry.addLine("Left Trigger: " + gamepad1.left_trigger);
        telemetry.addLine("Right Bumper: " + gamepad1.right_bumper);
        telemetry.addLine("Left Bumper: " + gamepad1.left_bumper);
        telemetry.addLine("Touchpad: " + gamepad1.touchpad);
    }
}
