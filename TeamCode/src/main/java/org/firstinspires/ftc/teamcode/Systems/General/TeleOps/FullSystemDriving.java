package org.firstinspires.ftc.teamcode.Systems.General.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;

@TeleOp(name="FullSystemDriving")
public class FullSystemDriving extends OpMode {

    Robot robot;

    ElapsedTime timer;
    @Override
    public void init() {
        robot = new Robot(telemetry, hardwareMap, false);
        timer = new ElapsedTime();

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {

        double x = -gamepad1.left_stick_y;
        double y = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        double leftFrontPower = x + y + r;
        double rightFrontPower = x - y - r;
        double leftBackPower = x - y + r;
        double rightBackPower = x + y - r;

        robot.setDrivePower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        robot.setIntakeSpeed(-gamepad1.right_trigger);
        robot.setIntakeSpeed(gamepad1.left_trigger);

        if(gamepad1.dpad_down){
            timer.reset();
            robot.setOutboardTarget(robot.outboardRetractedPos());
            robot.setYawTarget(robot.yawDownPos());
            robot.setBoxIntake();
        }





        telemetry.update();
    }
}
