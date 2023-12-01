package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="LiftTester")
public class LiftTester extends OpMode {
    CombineLiftC lift;

    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);

        telemetry.addLine("Ready for Start");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        lift.yawCalibrate();
    }

    public void loop(){

        lift.liftLoop();

        if(gamepad1.dpad_up){
            lift.setOutboardCts(-1);
        }
        if(gamepad1.dpad_down){
            lift.setOutboardCts(1);
        }
        if(gamepad1.dpad_right){
            lift.setYawCts(100);
        }
        if(gamepad1.dpad_left){
            lift.setYawCts(-100);
        }
        if(gamepad1.a){
            lift.setYawPower(0);
            lift.setOutboardPower(0);
        }

        telemetry.addLine("Yaw Motor Position: " + lift.getYawPos());

        telemetry.addLine("Outboard Motor Position: " + lift.getOutboardPos());

        telemetry.update();
    }
}
