package org.firstinspires.ftc.teamcode.Systems.General.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name= "Signal Finders")
public class RedRight extends LinearOpMode {
    public enum ROBOT_STATE{
        SEE,
        TO_DROPR,
        TO_DROPC,
        TO_DROPL,
        RUNNING,
        DROP,
        RETURNR,
        RETURNC,
        RETURNL,
        TO_PARKR,
        TO_PARKC,
        TO_PARKL


    }
    private SignalDetector pipeline;
    Robot robot;

    ROBOT_STATE mRobotState;
    String mPropLocation;
    private void detectObject(){
        mPropLocation = pipeline.getPropLocation();
        if (mPropLocation == "Left"){
            mRobotState = ROBOT_STATE.TO_DROPL;
        }
        else if(mPropLocation == "Center"){
            mRobotState = ROBOT_STATE.TO_DROPC;
        }
        else if(mPropLocation == "Right"){
            mRobotState = ROBOT_STATE.TO_DROPR;
        }


    }
    public void dropPixel(){
        robot.setIntakeSpeed(0.5);
        sleep(500);
        if(mPropLocation == "Left"){
            mRobotState = ROBOT_STATE.RETURNL;
        }
        else if(mPropLocation == "Center"){
            mRobotState = ROBOT_STATE.RETURNC;
        }
        else if(mPropLocation == "Right"){
            mRobotState = ROBOT_STATE.RETURNR;
        }
    }

    private void toDropL(){
        TrajectorySequence leftSignalPath1 = robot.autoDrive.trajectorySequenceBuilder(new Pose2d(10.75, -62.81, Math.toRadians(90.00)))
                .splineTo(new Vector2d(13.57, -41.60), Math.toRadians(82.43))
                .splineTo(new Vector2d(6.01, -33.00), Math.toRadians(131.33))
                .build();
        robot.autoDrive.setPoseEstimate(leftSignalPath1.start());

    }

    private void toDropC(){
        TrajectorySequence centerSignalPath1 = robot.autoDrive.trajectorySequenceBuilder(new Pose2d(10.16, -63.25, Math.toRadians(90.00)))
                .splineTo(new Vector2d(10.75, -35.07), Math.toRadians(88.79))
                .build();
        robot.autoDrive.setPoseEstimate(centerSignalPath1.start());
    }

    private void toDropR(){
        TrajectorySequence rightSignalPathPart1 = robot.autoDrive.trajectorySequenceBuilder(new Pose2d(9.71, -63.25, Math.toRadians(90.00)))
                .splineTo(new Vector2d(20.69, -38.63), Math.toRadians(65.97))
                .build();
        robot.autoDrive.setPoseEstimate(rightSignalPathPart1.start());
    }
    private void toParkL(){
        TrajectorySequence leftSignalPath2 = robot.autoDrive.trajectorySequenceBuilder(new Pose2d(6.01, -33.00, Math.toRadians(131.33)))
                .splineTo(new Vector2d(16.24, -43.97), Math.toRadians(144.46))
                .splineTo(new Vector2d(57.47, -58.21), Math.toRadians(-29.07))
                .build();
        robot.autoDrive.setPoseEstimate(leftSignalPath2.start());
    }
    private void toParkC(){
        TrajectorySequence centerSignalPath2 = robot.autoDrive.trajectorySequenceBuilder(new Pose2d(10.75, -35.07, Math.toRadians(88.79)))
                .splineTo(new Vector2d(16.68, -48.57), Math.toRadians(94.86))
                .splineTo(new Vector2d(51.83, -61.03), Math.toRadians(-19.52))
                .build();
        robot.autoDrive.setPoseEstimate(centerSignalPath2.start());
    }
    private void toParkR(){
        TrajectorySequence rightSignalPathPart2 = robot.autoDrive.trajectorySequenceBuilder(new Pose2d(20.69, -38.63, Math.toRadians(65.97)))
                .splineTo(new Vector2d(21.43, -48.87), Math.toRadians(11.77))
                .splineTo(new Vector2d(52.42, -56.43), Math.toRadians(-13.71))
                .build();
        robot.autoDrive.setPoseEstimate(rightSignalPathPart2.start());
    }
    @Override
    public void runOpMode() throws InterruptedException {


        pipeline = new SignalDetector(hardwareMap, telemetry);
        robot = new Robot(telemetry, hardwareMap, false);

        telemetry.addLine("Here we go");
        telemetry.update();
        mRobotState = ROBOT_STATE.SEE;
        waitForStart();

        if (isStopRequested()) return;



        ///////////////GAME START////////////////////////////////

        while (opModeIsActive()){

            switch(mRobotState){
                case SEE:
                    detectObject();
                    break;
                case TO_DROPL:
                    toDropL();
                    break;
                case TO_DROPC:
                    toDropC();
                    break;
                case TO_DROPR:
                    toDropR();
                    break;
                case DROP:
                    dropPixel();
                    break;
                case TO_PARKL:
                    toParkL();
                    break;
                case TO_PARKC:
                    toParkC();
                    break;
                case TO_PARKR:
                    toParkR();
                    break;

            }
            /*if(!robot.autoDrive.isBusy()){
                switch(mRobotState){
                    case TO_DROPL:
                        mRobotState = ROBOT_STATE.DROP;
                        break;
                }
            }*/



            telemetry.addLine("Prop Location: " + pipeline.getPropLocation());
            telemetry.update();
        }

    }
}
