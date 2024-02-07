package org.firstinspires.ftc.teamcode.Systems.Lift;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;

@TeleOp(name="LiftTester")
public class LiftTester extends OpMode {
    CombineLiftC lift;



    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);

        telemetry.addLine("Ready for Start");
        telemetry.update();
    }


    public void loop(){
        /*double liftPower = gamepad1.left_stick_y;
        double yawPower = gamepad1.right_stick_x;

        lift.setOutboardPower(liftPower);

        lift.setYawPower(yawPower);

        if(gamepad1.a){
            lift.yawCalibrate();
        }*/

//        if(gamepad1.dpad_right){
//            lift.setYawTargetPos(lift.getYawPos() + 25);
//            lift.setYawPower(0.2);
//        }
//
//        if(gamepad1.dpad_left){
//            lift.setYawTargetPos(lift.getYawPos() - 25);
//            lift.setYawPower(0.2);
//        }

//        if(gamepad1.dpad_up){
//            lift.setOutboardTargetPos(lift.getOutboardState() + 1);
//            lift.setOutboardPower(0.2);
//        }
//
//        if(gamepad1.dpad_down){
//            lift.setOutboardTargetPos(lift.getOutboardState() - 1);
//            lift.setOutboardPower(0.2);
//        }

        telemetry.addLine("Yaw Motor Position: " + lift.getYawPos());

        telemetry.addLine("Outboard Motor Position: " + lift.getOutboardPos());

        telemetry.addLine("Yaw Power: " + lift.yaw.getPower());

        telemetry.addLine("Outboard Power: " + lift.outboard.getPower());

        telemetry.addLine("Box? " + lift.getTouchState());

        telemetry.update();
    }
}
