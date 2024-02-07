package org.firstinspires.ftc.teamcode.Systems.hanging;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Hang Servo Position Finder")
public class HangServoPositionFinder extends OpMode {

    HangServo hangServo;
    @Override
    public void init() {
        hangServo = new HangServo(hardwareMap);

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0){
            hangServo.setPosition(hangServo.getPosition() + 0.001);
        }
        if(gamepad1.left_trigger > 0){
            hangServo.setPosition(hangServo.getPosition() - 0.001);
        }
        if(gamepad1.a){
            hangServo.setPosition(0);
        }
        if (gamepad1.y) {
            hangServo.setPosition(1);
        }

        telemetry.addLine("Use the right trigger for positive gain");
        telemetry.addLine("Use left trigger for negative gain");
        telemetry.addLine("Use A for 0 and Y for 1");
        telemetry.addLine("Trapdoor Servo Position: " + hangServo.getPosition());
    }
}
