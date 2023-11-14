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
    public void loop() {
        if(gamepad1.dpad_up){
            lift.setOutboardCts(lift.getOutboardPos() + 1);
        }
        if(gamepad1.dpad_down){
            lift.setOutboardCts(lift.getOutboardPos() -1);
        }
        if(gamepad1.dpad_right){
            lift.setYawCts(lift.getYawPos() + 1);
        }
        if(gamepad1.dpad_left){
            lift.setYawCts(lift.getYawPos() -1);
        }

        /*telemetry.addData("Outboard Counts:" + (lift.getOutboardPos()));

        telemetry.addData("Yaw Counts" + lift.getYawPos());
         */
    }
}
