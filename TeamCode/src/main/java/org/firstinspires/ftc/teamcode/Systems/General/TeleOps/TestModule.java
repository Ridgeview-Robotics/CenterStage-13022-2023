package org.firstinspires.ftc.teamcode.Systems.General.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Systems.Drone.DroneHolderServo;
import org.firstinspires.ftc.teamcode.Systems.Drone.Flywheels;
import org.firstinspires.ftc.teamcode.Systems.General.Drive;
import org.firstinspires.ftc.teamcode.Systems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.BoxServo;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.TrapdoorServo;

@TeleOp(name= "Test Module")
public class TestModule extends OpMode {
    CombineLiftC lift;

    Drive drive;
    BoxServo boxServo;
    TrapdoorServo trapdoorServo;
    Intake intake;
    DroneHolderServo droneHolderServo;
    Flywheels droneLauncher;

    double intakeFowPower;
    double intakeRevPower;
    @Override
    public void init() {
        lift = new CombineLiftC(hardwareMap);

        drive = new Drive(hardwareMap);
        boxServo = new BoxServo(hardwareMap);
        trapdoorServo = new TrapdoorServo(hardwareMap, telemetry);
        intake = new Intake(hardwareMap);
        droneLauncher = new Flywheels(hardwareMap);
        droneHolderServo = new DroneHolderServo(hardwareMap);


        lift.resetLiftEncoders();

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        int yawPos = lift.getYawPos();
        int outboardPos = lift.getOutboardPos();

        double y = -(gamepad1.left_stick_y);
        double x = (gamepad1.left_stick_x);
        double r = gamepad1.right_stick_x;

        double leftFrontPower = y + x + r;
        double rightFrontPower = y - x - r;
        double leftBackPower = y - x + r;
        double rightBackPower = y + x - r;

        drive.setMotorPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        intake.setIntakePower(-gamepad1.right_trigger);
        intake.setIntakePower(gamepad1.left_trigger);

//        if(gamepad1.dpad_left){
//            lift.setYawTargetPos(yawPos + 10);
//        }
//        if (gamepad1.dpad_right) {
//            lift.setYawTargetPos(yawPos - 10);
//        }
//        if(gamepad1.dpad_up){
//            lift.setOutboardTargetPos(outboardPos + 10);
//        }
//        if(gamepad1.dpad_down){
//            lift.setOutboardTargetPos(outboardPos - 10);
//        }

        if(gamepad1.a){
            boxServo.setPosition(boxServo.getPosition() - 0.001);
        }
        if(gamepad1.y){
            boxServo.setPosition(boxServo.getPosition() + 0.001);
        }
        if(gamepad1.x){
            trapdoorServo.GSetPosition(trapdoorServo.getPosition() - 0.001);
        }
        if(gamepad1.b){
            trapdoorServo.GSetPosition(trapdoorServo.getPosition() + 0.001);
        }



        telemetry.addLine("Use Y (+) and A (-) for box");
        telemetry.addLine("Box Position: " + boxServo.getPosition());
        telemetry.addLine("Use B (+) and X (-) for trapdoor");
        telemetry.addLine("Trapdoor Position" + trapdoorServo.getPosition());
        telemetry.addLine("Yaw Counts: " + lift.getYawPos());
        telemetry.addLine("Outboard Counts: " + lift.getOutboardPos());
        telemetry.addLine("Box Touch = " + lift.getTouchState());
        telemetry.addLine("Use Left (+) and Right (-) bumpers for Drone Servo");
        telemetry.addLine("Drone Servo Position: " + droneHolderServo.getDroneServoPos());

        //make calculations for extension length and angle maxes
        telemetry.update();
    }
}
