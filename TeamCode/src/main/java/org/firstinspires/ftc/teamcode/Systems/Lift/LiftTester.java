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


    public void loop(){
        double liftPower = gamepad1.left_stick_x;

        lift.outboard.setPower(liftPower);

        telemetry.addLine("Yaw Motor Position: " + lift.getYawPos());

        telemetry.addLine("Outboard Motor Position: " + lift.getOutboardPos());

        telemetry.update();
    }
}
