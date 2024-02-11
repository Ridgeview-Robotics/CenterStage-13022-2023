package org.firstinspires.ftc.teamcode.Systems.General.TeleOps;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.TrapdoorServo;
import org.firstinspires.ftc.teamcode.Systems.hanging.HangServo;

/*         Intake Exhume                        Intake Intake
             _=====_                               _=====_
            / _____ \                             / _____ \
           +.-'_____'-.---------------------------.-'_____'-.+
          /   |LIFT HIGH|  '.        S O N Y        .'  |  _  |   \
         / ___| /|\ |___ \                     / ___| /T\ |___ \
        / |      |      | ; LIFT FIRST LINE __; | _         _ | ;
        | | <---   ---> | | |__|         |_:> | ||S|       (O)| |
        | |___   |   ___| ;                   ; |___       ___| ;
        |\    | \|/ |    /  _     ___      _   \    | (X) |    /|
        | \   |_____|  .','" "', |___|  ,'" "', '.  |_____|  .' |
        |  LIFT DOWN    /       \ANALOG/       \  '-._____.-'   |
        |               |       |------|       |                |
        |              /\       /      \       /\               |
        |             /  '.___.'        '.___.'  \              |
        |            /                            \             |
         \          /                              \           /
          \________/                                \_________/

*/


@TeleOp(name="FullSystemDriving")
public class FullSystemDriving extends OpMode {



    Robot robot;

    ElapsedTime timer;

    RevBlinkinLedDriver.BlinkinPattern rainbow;

    public boolean mCanHang = false;


    @Override
    public void init() {
        robot = new Robot(telemetry, hardwareMap, false);
        timer = new ElapsedTime();
        rainbow = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE;
        robot.lights.setPattern(rainbow);
//        robot.hangMotor.stopAndReset();
//        robot.hangMotor.runToPosMode();


        robot.primeDrone();
        robot.setBoxIntake();

        telemetry.addLine("Ready for Start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        //constant robot position updates
        robot.robotUpdate();

        //normalizer addition
        double max;

        //define powers
        double x = -gamepad1.left_stick_y;
        double y = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        //assn powers
        double leftFrontPower = x + y + r;
        double rightFrontPower = x - y - r;
        double leftBackPower = x - y + r;
        double rightBackPower = x + y - r;

        //normalization
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        //set drive power
        robot.setDrivePower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        //trapdoor toggle
        if(gamepad1.a){
            robot.trapdoorTogglePosition();
        }

        //lift to high
        if(gamepad1.dpad_up){

            robot.setBoxScore();
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.HIGHEST, CombineLiftC.yawPositions.SCORE, CombineLiftC.yawPositions.CLEAR);
        }

        //lift to middle
        if(gamepad1.left_bumper){

            robot.setBoxScore();
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.MIDDLE, CombineLiftC.yawPositions.SCORE, CombineLiftC.yawPositions.CLEAR);
        }

        //lift to first line
        if(gamepad1.right_bumper){

            robot.setBoxScore();
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.FIRST_LINE, CombineLiftC.yawPositions.SCORE, CombineLiftC.yawPositions.CLEAR);
        }

        //lift to no extension, score.
        if(gamepad1.dpad_down){
            robot.setBoxScore();
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.DOWN, CombineLiftC.yawPositions.SCORE, CombineLiftC.yawPositions.CLEAR);
        }

        //lift to clearance position
        if(gamepad1.b){
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.DOWN, CombineLiftC.yawPositions.CLEAR, CombineLiftC.yawPositions.CLEAR);
            robot.setBoxStorage();
        }

        //set box down
        if(gamepad1.x){
            robot.setBoxIntake();
            robot.setTrapdoor(TrapdoorServo.trapdoorPositions.OPEN);
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.DOWN, CombineLiftC.yawPositions.DOWN, CombineLiftC.yawPositions.CLEAR);
        }

        //drone launching
        if(gamepad1.ps){
            robot.shootDrone();
        }



        //sets intake correct directions
        if(gamepad1.right_trigger > 0.0){
            robot.setIntakeSpeed(-0.6*gamepad1.right_trigger);
        }
        else if(gamepad1.left_trigger > 0.0) {
            robot.setIntakeSpeed(0.75*gamepad1.left_trigger);
        }
        else{
            robot.setIntakeSpeed(0);
        }


        //hanging
//        if(gamepad1.share){
//            robot.drive.stopAndReset();
//            robot.drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.hangServo.setRaisedPosition();
//            robot.hangMotor.setFloat();
//            robot.hangMotor.setTarget(robot.hangMotor.getHangPos());
//            robot.lift.setYawPower(0.5);
//            robot.lift.setOutboardPower(0.1);
//            robot.hangMotor.setHangMotorPower(1.0);
//            robot.drive.setMotorPower(0.3, 0.3, 0.3, 0.3);
//            robot.drive.setDriveTarget(12);
//            while(robot.drive.isBusy()){
//
//            }
//            robot.drive.setDriveTarget(-6);
//            while(robot.drive.isBusy()){
//
//            }
//            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.HANG_TARGET_POS, CombineLiftC.yawPositions.HANG_POS, CombineLiftC.yawPositions.CLEAR);
//            robot.boxServo.setBoxHang();
//            robot.drive.setDriveTarget(16);
//            while(robot.drive.isBusy()){
//
//            }
//            robot.hangServo.setDownPosition();
//            robot.lift.setOutboardTargetBasic(robot.lift.getOutboardPos() - 150);
//            robot.hangMotor.setHangtoHang();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            robot.lift.setOutboardTargetPos(CombineLiftC.outboardPositions.HANG_TARGET_POS);
//        }

        if(gamepad2.a){
            robot.hangServo.setRaisedPosition();
            robot.liftWithClearanceCheck(CombineLiftC.outboardPositions.HIGH_HANG_TARGET, CombineLiftC.yawPositions.HANG_POS, CombineLiftC.yawPositions.CLEAR);
            robot.boxServo.setBoxHang();
        }

        //drive here

        //and drive back

        if(gamepad2.b){
            robot.hangServo.setDownPosition();
            robot.lift.setOutboardTargetPos(CombineLiftC.outboardPositions.HANG_TARGET_POS);
        }

        //back up slightly

        if (gamepad2.y) {
            robot.hangMotor.runWithEncoderMode();
            robot.lift.outboard.runUsingEncoderMode();
            robot.hangMotor.setHangMotorPower(0);
            robot.lift.setOutboardPower(0);
            mCanHang = true;
        }

        //GABE IS UP

        if(mCanHang) {
            if (gamepad2.left_trigger > 0.0) {
                robot.hangMotor.setHangMotorPower(gamepad2.left_trigger);
            } else {
                robot.hangMotor.setHangMotorPower(0);
            }

            if (gamepad2.right_trigger > 0.0) {
                robot.lift.outboard.setPower(-gamepad2.right_trigger);
            } else {
                robot.lift.outboard.setPower(0);
            }
        }




        telemetry.addLine("Yaw Cts:" + robot.lift.yaw.getPos());
        telemetry.addLine("Outboard Cts: " + robot.lift.outboard.getPos());
        telemetry.addLine("Yaw State: " + robot.lift.getYawState());
        telemetry.addLine("Outboard State: " + robot.lift.getOutboardState());
        telemetry.addLine("mCheckerPos Boolean: " + robot.lift.mCheckerPos);
        telemetry.addLine("Boundary Position: " + robot.lift.mBoundaryPosition);
        telemetry.addLine("Trapdoor State: " + Robot.mTrapdoorPos);

        telemetry.update();
    }
}
