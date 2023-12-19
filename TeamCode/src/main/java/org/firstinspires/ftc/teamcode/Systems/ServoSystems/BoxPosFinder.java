package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Box Position Finder")
public class BoxPosFinder extends OpMode {
    BoxServo boxServo;
    @Override
    public void init() {
        boxServo = new BoxServo(hardwareMap);

        telemetry.addLine("Ready For Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0){
            boxServo.setPosition(boxServo.getPosition() + 0.001);
        }
        if(gamepad1.left_trigger > 0){
            boxServo.setPosition(boxServo.getPosition() - 0.001);
        }
        if(gamepad1.a){
            boxServo.setPosition(0);
        }
        if (gamepad1.y) {
            boxServo.setPosition(1);
        }

        telemetry.addLine("Use the right trigger for positive gain");
        telemetry.addLine("Use left trigger for negative gain");
        telemetry.addLine("Use A for 0 and Y for 1");
        telemetry.addLine("Box Servo Position: " + boxServo.getPosition());
    }
}
