package org.firstinspires.ftc.teamcode.Systems.General;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Systems.Drone.Flywheels;
import org.firstinspires.ftc.teamcode.Systems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.BoxServo;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.TrapdoorServo;
import org.firstinspires.ftc.teamcode.Systems.hanging.HangMotor;
import org.firstinspires.ftc.teamcode.Systems.hanging.HangServo;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Robot {
    public SampleMecanumDrive autoDrive;

    public CombineLiftC lift;
    public Drive drive;
    BoxServo boxServo;
    TrapdoorServo trapdoorServo;
    Intake intake;
    Flywheels drone;
    SignalDetector signalDetector;
    ElapsedTime timer;
    public HangServo hangServo;
    public HangMotor hangMotor;

    public RevBlinkinLedDriver lights;
    public static TrapdoorServo.trapdoorPositions mTrapdoorPos = TrapdoorServo.trapdoorPositions.OPEN;

    public Robot(Telemetry telemetry, HardwareMap hardwareMap, boolean isAuto){
        autoDrive = new SampleMecanumDrive(hardwareMap);
        lift = new CombineLiftC(hardwareMap);
        drive = new Drive(hardwareMap);
        boxServo = new BoxServo(hardwareMap);
        trapdoorServo = new TrapdoorServo(hardwareMap, telemetry);
        intake = new Intake(hardwareMap);
        drone = new Flywheels(hardwareMap);
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "LEDs");
        timer = new ElapsedTime();
        hangServo = new HangServo(hardwareMap);
        hangMotor = new HangMotor(hardwareMap);

        trapdoorServo.setTrapdoorOpen();

        /*if(isAuto){
            signalDetector = new SignalDetector(hardwareMap, telemetry, );
        }*/

    }

    public void robotUpdate(){
        trapdoorServo.update();
        lift.yawClearanceCkr();
        if(lift.mCheckerPos){
            lift.setOutboardTargetPos(lift.mNOutPosition);
            trapdoorServo.LSetPosition(mTrapdoorPos);
        }
        //;lift update carry position from lift
    }

    public void trapdoorTogglePosition(){
        trapdoorServo.togglePosition();
    }

    public void liftWithClearanceCheck(CombineLiftC.outboardPositions nOPos, CombineLiftC.yawPositions nYPos, CombineLiftC.yawPositions nBPos){
//        lift.mCheckerPos = false;
        lift.mNOutPosition = nOPos;
       lift.yawStateAssigner(nYPos);
       lift.outboardStateAssigner(nOPos, CombineLiftC.yawPositions.CLEAR);

    }

    public void liftTest(CombineLiftC position){

    }

    public void setTrapdoor(TrapdoorServo.trapdoorPositions position){
        mTrapdoorPos = position;
//        trapdoorServo.LSetPosition(position);
    }


    public void setDrivePower(double power1, double power2, double power3, double power4){
        drive.setMotorPower(power1, power2, power3, power4);
    }

//    public void setYawTarget(int pos){
//        lift.setYawTargetPos(pos);
//    }



    public void setYawPower(double power){
        lift.setYawPower(power);
    }

    public void setOutboardPower(double power){
        lift.setOutboardPower(power);
    }

    public void setIntakeSpeed(double speed){
        intake.setIntakePower(speed);
    }

    public void setBoxScore(){
        boxServo.setBoxScore();
    }

    public void setBoxIntake(){
        boxServo.setBoxIntake();
    }
    public void setBoxStorage(){boxServo.setBoxStorage();}

    public void setTrapdoorOpen(){
        trapdoorServo.setTrapdoorOpen();
    }

    public void setTrapdoorClosed(){
        trapdoorServo.setTrapdoorClosed();
    }



    public int yawDownPos(){
        return CombineLiftC.yawDownPos;
    }

    public int outboardRetractedPos(){
        return CombineLiftC.outboardRetractedPos;
    }

    public void setYawScore(){
        lift.setYawScore();
    }

    public void setYawDown(){
        lift.setYawDown();
    }

    public void setOutboardFirstLine(){
        lift.setOutboardFirstLinePos();
    }
    public void setOutboardTop(){
        lift.setOutboardHighestPos();
    }
    public void setOutboardRetracted(){
        lift.setOutboardRetracted();
    }

    public void toHighScore(){
            while(lift.getYawPos() < 1600){
                lift.setYawScore();
            }
            while(lift.getYawPos() < 1800){
                lift.setOutboardHighestPos();
            }
            while(lift.getOutboardPos() < 1600){
                boxServo.setBoxScore();

            }
    }

    public void toFirstLine(){
        while(lift.getYawPos() < 1000){
            setYawScore();
        }
        while(lift.getYawPos() < 1500){
            setOutboardFirstLine();
            setBoxScore();
        }
//        while()
//
//
//            setTrapdoorClosed();
//
//
//            //at end
//            setTrapdoorOpen();
    }

    public void toIntake(){
            setYawDown();
            setOutboardRetracted();
            setBoxIntake();
            setTrapdoorClosed();
    }


    public void liftCalibration(){
        lift.yawCalibrate();
    }

    public void shootDrone(){
        drone.shootDrone();
    }



    public void primeDrone(){
        drone.primeDrone();
    }



    /*Trapdoor:
        Open: 0.365   Closed: 0.65

      Box:
        Down:1.00   Scoring: 0.833

      Yaw Scoring Pos: 1800

      Outboard:
        First Line: 695
        Second Line/Highest Position: 1600

Finished Autos:
STAGE1:
RL, RR, BL, BR

STAGE2:
RL, RR




    */
}
