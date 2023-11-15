package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LiftTester extends OpMode {
    CombineLiftC lift;

    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);

        telemetry.addLine("Ready for Start");
        telemetry.update();
    }

    @Override
    public void loop(){

        if(gamepad1.dpad_up){
            lift.setOutboardCts(1);
        }
        if(gamepad1.dpad_down){
            lift.setOutboardCts(-1);
        }
        if(gamepad1.dpad_right){
            lift.setYawCts(1);
        }
        if(gamepad1.dpad_left){
            lift.setYawCts(-1);
        }
        if(gamepad1.a){
            lift.setYawPower(0);
            lift.setOutboardPower(0);
        }

        telemetry.addLine("Yaw Motor Position" + lift.getOutboardPos());

        telemetry.addLine("Outboard Motor Position" + lift.getOutboardPos());

    }
}
