package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Trapdoor Position Finder")
public class TrapdoorPosFinder extends OpMode {
    TrapdoorServo trapdoorServo;
    @Override
    public void init() {
        trapdoorServo = new TrapdoorServo(hardwareMap, telemetry);

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0){
            trapdoorServo.GSetPosition(trapdoorServo.getPosition() + 0.001);
        }
        if(gamepad1.left_trigger > 0){
            trapdoorServo.GSetPosition(trapdoorServo.getPosition() - 0.001);
        }
        if(gamepad1.a){
            trapdoorServo.GSetPosition(0);
        }
        if (gamepad1.y) {
            trapdoorServo.GSetPosition(1);
        }

        telemetry.addLine("Use the right trigger for positive gain");
        telemetry.addLine("Use left trigger for negative gain");
        telemetry.addLine("Use A for 0 and Y for 1");
        telemetry.addLine("Trapdoor Servo Position: " + trapdoorServo.getPosition());
    }
}
