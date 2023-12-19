package org.firstinspires.ftc.teamcode.Systems.General;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.Systems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.BoxServo;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.TrapdoorServo;

import java.util.concurrent.TransferQueue;

@TeleOp(name= "Test Module")
public class TestModule extends OpMode {
    CombineLiftC lift;
    TouchSensor liftTouch;
    TouchSensor boxTouch;
    Drive drive;
    BoxServo boxServo;
    TrapdoorServo trapdoorServo;
    Intake flywheels;

    double flywheelPow;
    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);
        liftTouch = hardwareMap.get(TouchSensor.class, "yawTouch");
        boxTouch = hardwareMap.get(TouchSensor.class, "boxTouch");
        drive = new Drive(hardwareMap);
        boxServo = new BoxServo(hardwareMap);
        trapdoorServo = new TrapdoorServo(hardwareMap);
        flywheels = new Intake(hardwareMap);

        lift.resetLiftEncoders();

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {

        lift.liftLoop();

        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        double leftFrontPower = y + x + r;
        double rightFrontPower = y - x - r;
        double leftBackPower = y - x + r;
        double rightBackPower = y + x - r;

        drive.setMotorPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        flywheels.setIntakePower(flywheelPow);

        if(gamepad1.right_trigger >= 0){
            flywheelPow = gamepad1.right_trigger;
        }

        if(gamepad1.dpad_left){
            lift.setYawCts(1);
        }
        if (gamepad1.dpad_right) {
            lift.setYawCts(-1);
        }
        if(gamepad1.dpad_up){
            lift.setOutboardCts(1);
        }
        if(gamepad1.dpad_down){
            lift.setOutboardCts(-1);
        }

        if(gamepad1.a){
            boxServo.setPosition(boxServo.getPosition() - 0.001);
        }
        if(gamepad1.y){
            boxServo.setPosition(boxServo.getPosition() + 0.001);
        }
        if(gamepad1.x){
            trapdoorServo.setPosition(trapdoorServo.getPosition() - 0.001);
        }
        if(gamepad1.b){
            trapdoorServo.setPosition(trapdoorServo.getPosition() + 0.001);
        }

        telemetry.addLine("Use Y (+) and A (-) for box");
        telemetry.addLine("Box Position: " + boxServo.getPosition());
        telemetry.addLine("Use B (+) and X (-) for trapdoor");
        telemetry.addLine("Trapdoor Position" + trapdoorServo.getPosition());
        telemetry.addLine("Yaw Counts: " + lift.getYawPos());
        telemetry.addLine("Outboard Counts: " + lift.getOutboardPos());
        telemetry.addLine("is Lift sensor touching? " + liftTouch.isPressed());
        telemetry.addLine("is Box sensor touching? " + boxTouch.isPressed());
        //make calculations for extension length and angle maxes
        telemetry.update();
    }
}
