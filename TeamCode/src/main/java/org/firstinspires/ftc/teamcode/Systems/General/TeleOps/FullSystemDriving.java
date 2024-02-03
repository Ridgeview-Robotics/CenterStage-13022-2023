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
        robot.robotUpdate();
        double max;



        double x = -gamepad1.left_stick_y;
        double y = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        double leftFrontPower = x + y + r;
        double rightFrontPower = x - y - r;
        double leftBackPower = x - y + r;
        double rightBackPower = x + y - r;

        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }



        if(gamepad1.a){
                robot.trapdoorTogglePosition();

//            if(wasAPressed > 0){
//                if((getRuntime() - wasAPressed) > 0.5){
//                    wasAPressed = 0;
//                }
//                else{
//                    return;
//                }
//            }
//            wasAPressed = getRuntime();



        }

        if(gamepad1.b){
            robot.setTrapdoorOpen();
        }

        robot.setDrivePower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);

        robot.setIntakeSpeed(-2*gamepad1.right_trigger);
        robot.setIntakeSpeed(gamepad1.left_trigger);
        if(gamepad1.left_bumper){
            robot.setDronePower(0.29);
        }
        else{
            robot.setDronePower(0);
        }

        if(gamepad1.right_bumper){
            robot.shootDrone();
        }

//        if(gamepad1.a){
//            robot.setYawTarget(1750);
//            robot.setYawPower(0.6);
//        }

        if(gamepad1.dpad_left){
            robot.setOutboardTarget(525); //top of 1st line
            robot.setOutboardPower(0.75);
            robot.setBoxScore();
        }

        if(gamepad1.dpad_right){
            robot.setOutboardTarget(1000);
            robot.setOutboardPower(0.75);
            robot.setBoxScore();
        }

        if(gamepad1.right_stick_button){
            robot.setYawTarget(350);
            robot.setBoxStorage();
        }

        if(gamepad1.dpad_up){
            robot.setOutboardTarget(1950);
            robot.setOutboardPower(0.75);
            robot.setBoxScore();
        }

        if(gamepad1.dpad_down){
            robot.setOutboardTarget(0);
            robot.setOutboardPower(0.5);
        }

//        if(gamepad1.a){
//            while(robot.pos)
//        }

        if(gamepad1.x){
            robot.setTrapdoorOpen();
        }

        if(gamepad1.y){
            robot.setTrapdoorClosed();
        }

//        if(gamepad1.b){
//            robot.setYawTarget(0);
//            robot.setYawPower(0.6);
//            robot.setOutboardTarget(0);
//            robot.setTrapdoorClosed();
//            robot.setBoxIntake();
//        }
        telemetry.update();
    }
}
