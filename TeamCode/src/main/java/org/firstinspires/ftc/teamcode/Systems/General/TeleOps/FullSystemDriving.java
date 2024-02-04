package org.firstinspires.ftc.teamcode.Systems.General.TeleOps;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;

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


    @Override
    public void init() {
        robot = new Robot(telemetry, hardwareMap, false);
        timer = new ElapsedTime();
        rainbow = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_RAINBOW_PALETTE;
        robot.lights.setPattern(rainbow);

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
        if(gamepad1.x){
            robot.trapdoorTogglePosition();
        }

        //lift to high
        if(gamepad1.y && gamepad1.dpad_up){
            robot.setTrapdoorClosed();
            robot.setBoxScore();
            robot.liftWithClearanceCheck("High", "Score");
        }

        //lift to middle
        if(gamepad1.y && gamepad1.dpad_left){
            robot.setTrapdoorClosed();
            robot.setBoxScore();
            robot.liftWithClearanceCheck("Middle", "Score");
        }

        //lift to first line
        if(gamepad1.y && gamepad1.dpad_right){
            robot.setTrapdoorClosed();
            robot.setBoxScore();
            robot.liftWithClearanceCheck("First Line", "Score");
        }

        //lift to clearance position
        if(gamepad1.b){
            robot.liftWithClearanceCheck("Retracted", "Clearance");
            robot.setBoxStorage();
        }

        //set box down
        if(gamepad1.a){
            robot.setBoxIntake();
            robot.liftWithClearanceCheck("Retracted", "Down");
        }

        //drone launching
        if(gamepad1.share){
            robot.shootDrone();
        }

        //hanging system
        if(gamepad1.options){
            
        }

        //sets intake correct directions
        robot.setIntakeSpeed(-gamepad1.right_trigger);
        robot.setIntakeSpeed(gamepad1.left_trigger);

        telemetry.update();
    }
}
