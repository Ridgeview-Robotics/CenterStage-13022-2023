package org.firstinspires.ftc.teamcode.Systems.Lift;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;

@TeleOp(name="LiftTester")
public class LiftTester extends OpMode {
    CombineLiftC lift;

    double obPower;

    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);

        telemetry.addLine("Ready for Start");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        lift.yawCalibrate();

        obPower = 0;
    }

    public void loop(){

        lift.liftLoop();

        /*if(gamepad1.dpad_up){
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
        if(gamepad1.y){
            lift.setOutboardPower(-1);
        }
        else{
            lift.setOutboardPower(0);
        }
        if(gamepad1.a){
            lift.setOutboardPower(1);
        }
        else{
            lift.setOutboardPower(0);
        }
        if(gamepad1.x){
            lift.setYawPower(1);
        }
        else{
            lift.setYawPower(0);
        }
        if(gamepad1.b){
            lift.setYawPower(-1);
        }
        else{
            lift.setYawPower(0);
        }


        double obPower = gamepad1.left_stick_y;

        lift.setOutboardPower(obPower);*/

        if(gamepad1.y){
            obPower = 1;
        }

        lift.setOutboardPower(obPower);

        if(gamepad1.x){
            obPower = 0;
        }

        if(gamepad1.a){
            obPower = -1;
        }

        telemetry.addLine("Yaw Motor Position: " + lift.getYawPos());

        telemetry.addLine("Outboard Motor Position: " + lift.getOutboardPos());

        telemetry.update();
    }
}
