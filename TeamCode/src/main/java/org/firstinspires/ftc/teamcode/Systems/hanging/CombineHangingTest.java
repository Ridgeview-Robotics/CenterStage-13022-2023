package org.firstinspires.ftc.teamcode.Systems.hanging;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;


@TeleOp(name="Combine Hang Test")
public class CombineHangingTest extends OpMode {

    CombineLiftC lift;
    HangMotor hangMotor;
    HangServo hangServo;
    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);
        hangMotor = new HangMotor(hardwareMap);
        hangServo = new HangServo(hardwareMap);

        hangMotor.runWithEncoderMode();
        lift.yaw.runUsingEncoderMode();
        lift.outboard.runUsingEncoderMode();

        lift.setOutboardPower(0);
        lift.setYawPower(0);
    }

    @Override
    public void loop() {
        lift.setYawPower(gamepad1.left_stick_y);
        lift.setOutboardPower(gamepad1.right_stick_y);

        if(gamepad1.right_bumper){
            hangServo.setPosition(hangServo.getPosition() + 0.001);
        }

        if(gamepad1.left_bumper){
            hangServo.setPosition(hangServo.getPosition() - 0.001);
        }

        if(gamepad1.right_trigger > 0.0){
            hangMotor.setHangMotorPower(gamepad1.right_trigger);
        }
        else if(gamepad1.left_trigger > 0.0){
            hangMotor.setHangMotorPower(-gamepad1.left_trigger);
        }
        else{
            hangMotor.setHangMotorPower(0.0);
        }


        telemetry.addLine("Outboard Encoder Position: " + lift.getOutboardPos());
        telemetry.addLine("Yaw Encoder Position: " + lift.getYawPos());
        telemetry.addLine("Lift Encoder Position: " + hangMotor.getHangPos());
        telemetry.addLine("Hang Power: " + hangMotor.getPower());
        telemetry.addLine("Outboard Power: " + lift.outboard.getPower());
        telemetry.addLine("Yaw Power: " + lift.yaw.getPower());
        telemetry.addLine("Hang Servo Pos: " + hangServo.getPosition());
    }
}
