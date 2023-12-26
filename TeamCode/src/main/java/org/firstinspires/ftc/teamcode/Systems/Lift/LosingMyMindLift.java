package org.firstinspires.ftc.teamcode.Systems.Lift;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Losing my mind")
public class LosingMyMindLift extends OpMode {

    CombineLiftC lift;

    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);


        telemetry.update();
        telemetry.addLine("ready for start");
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            lift.setYawTargetPos(10);
        }

        telemetry.addLine("yaw pos" + lift.getYawPos());
        telemetry.update();
    }
}
