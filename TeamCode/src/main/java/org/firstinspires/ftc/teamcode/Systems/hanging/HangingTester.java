package org.firstinspires.ftc.teamcode.Systems.hanging;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name= "Hanging Tester")
public class HangingTester extends OpMode {

    HangMotor hangMotor;
    HangServo hangServo;

    @Override
    public void init() {
        hangMotor = new HangMotor(hardwareMap);
        hangServo = new HangServo(hardwareMap);

        hangMotor.runWithEncoderMode();

        telemetry.addLine("ready");
        telemetry.update();
    }

    @Override
    public void loop() {
        hangMotor.setHangMotorPower(gamepad1.left_stick_x);

        if(gamepad1.x){
            hangMotor.runToPosMode();
        }

        if(gamepad1.b){
            hangMotor.runWithEncoderMode();
        }

        if(gamepad1.a){
            hangServo.setPosition(0);
        }

        if(gamepad1.y){
            hangServo.setPosition(1);
        }

        if(gamepad1.right_trigger > 0.0){
            hangServo.setPosition(hangServo.getPosition() + 0.001);
        }

        if(gamepad1.left_trigger > 0.0){
            hangServo.setPosition(hangServo.getPosition() - 0.001);
        }

        telemetry.update();
        telemetry.addLine("Servo- A to Position0  Y to Position1");
        telemetry.addLine("Manually adjust servo with triggers");
        telemetry.addLine("Setting Modes-  X:toPos  B:w/enc");
        telemetry.addLine("Motor Position: ");
    }
}
