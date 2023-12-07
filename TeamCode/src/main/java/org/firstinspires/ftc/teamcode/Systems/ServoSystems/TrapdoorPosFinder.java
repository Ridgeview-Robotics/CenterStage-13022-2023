package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Trapdoor Position Finder")
public class TrapdoorPosFinder extends OpMode {
    TrapdoorServo trapdoorServo;
    @Override
    public void init() {
        trapdoorServo = new TrapdoorServo(hardwareMap);

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0){
            trapdoorServo.setPosition(trapdoorServo.getPosition() + 0.001);
        }
        if(gamepad1.left_trigger > 0){
            trapdoorServo.setPosition(trapdoorServo.getPosition() - 0.001);
        }
        if(gamepad1.a){
            trapdoorServo.setPosition(0);
        }
        if (gamepad1.y) {
            trapdoorServo.setPosition(1);
        }

        telemetry.addLine("Use the right tigger for positive gain");
        telemetry.addLine("Use left trigger for negative gain");
        telemetry.addLine("Use A for 0 and Y for 1");
        telemetry.addLine("Trapdoor Servo Position: " + trapdoorServo.getPosition());
    }
}
