package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage2;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;

import kotlin.collections.ArrayDeque;

@Autonomous(name = "RedLeft(st2)")
public class RedLeft2 extends LinearOpMode {

    private enum ROBOT_STATE{
        SEE,
        TO_DROP,
        RUNNING,
        DROP,
        GO_TO_SCORE,
        SCORE,
        RETURN_AND_PARK,
        IDLE


    }

    private enum PROP_LOC{
        LEFT(0),
        CENTER(1),
        RIGHT(2),
        NONE(3);

        private final int location;

        PROP_LOC(final int newLocation){
            location = newLocation;
        }

        private int getLocation(){
            return location;
        }
    }

    private PROP_LOC mPropLoc;
    private SignalDetector mPipeline;
    Robot mRobot;

    ROBOT_STATE mRobotState;
    boolean mIsRoadRunning;
    List<TrajectorySequence> mDropPixelSequences;
    List<TrajectorySequence> mOffToScoreSequences;
    List <TrajectorySequence> mReturnAndParkSequences;


    ///SEQUENCES HERE///

    private void setupDropSequences(){
        TrajectorySequence leftToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)))
                .splineTo(new Vector2d(-43.82, -41.01), Math.toRadians(122.30))
                .build();
        mDropPixelSequences.add(leftToPixel);

        TrajectorySequence centerToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)))
                .splineTo(new Vector2d(-33.59, -33.74), Math.toRadians(88.93))
                .build();
        mDropPixelSequences.add(centerToPixel);

        TrajectorySequence rightToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)))
                .splineTo(new Vector2d(-44.27, -47.68), Math.toRadians(93.69))
                .splineTo(new Vector2d(-31.96, -39.37), Math.toRadians(33.23))
                .build();
        mDropPixelSequences.add(rightToPixel);

    }

    private void setupScoringSequences(){
        TrajectorySequence leftToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-43.82, -41.01, Math.toRadians(122.30)))
                .lineTo(new Vector2d(-32.40, -35.81))
                .splineTo(new Vector2d(-39.67, -7.19), Math.toRadians(90.00))
                .splineTo(new Vector2d(8.97, -6.60), Math.toRadians(7.68))
                .splineTo(new Vector2d(25.88, -17.87), Math.toRadians(180.00))
                .lineTo(new Vector2d(50.94, -30.18))
                .build();
        mOffToScoreSequences.add(leftToScore);

        TrajectorySequence centerToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-33.59, -33.74, Math.toRadians(88.93)))
                .lineTo(new Vector2d(-51.24, -46.79))
                .splineTo(new Vector2d(-54.20, -14.90), Math.toRadians(93.38))
                .splineTo(new Vector2d(-13.12, 8.38), Math.toRadians(0.00))
                .splineTo(new Vector2d(24.99, -10.90), Math.toRadians(180.00))
                .lineTo(new Vector2d(50.94, -36.26))
                .build();
        mOffToScoreSequences.add(centerToScore);

        TrajectorySequence rightToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-31.96, -39.37, Math.toRadians(33.23)))
                .lineTo(new Vector2d(-56.73, -38.63))
                .splineTo(new Vector2d(-45.01, -14.76), Math.toRadians(66.36))
                .splineTo(new Vector2d(1.41, 7.49), Math.toRadians(18.43))
                .splineTo(new Vector2d(31.37, -10.31), Math.toRadians(180.00))
                .lineTo(new Vector2d(50.79, -42.34))
                .build();
        mOffToScoreSequences.add(rightToScore);
    }

    private void setupParkSequences(){
        TrajectorySequence leftToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.94, -36.26, Math.toRadians(180.00)))
                .lineTo(new Vector2d(35.81, -9.27))
                .lineTo(new Vector2d(61.17, -11.05))
                .build();
        mReturnAndParkSequences.add(leftToPark);

        TrajectorySequence centerToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.94, -36.26, Math.toRadians(180.00)))
                .lineTo(new Vector2d(35.81, -9.27))
                .lineTo(new Vector2d(61.17, -11.05))
                .build();

        mReturnAndParkSequences.add(centerToPark);

        TrajectorySequence rightToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.94, -30.18, Math.toRadians(180.00)))
                .lineTo(new Vector2d(35.81, -9.27))
                .lineTo(new Vector2d(61.17, -11.05))
                .build();
        mReturnAndParkSequences.add(rightToPark);

    }

    private void detectObject(){
        sleep(100);
        String loc = mPipeline.getRedPropLocation();
        if (loc == "Left"){
            mPropLoc = PROP_LOC.LEFT;
        }
        else if(loc == "Center"){
            mPropLoc = PROP_LOC.CENTER;
        }
        else if(loc == "Right"){
            mPropLoc = PROP_LOC.RIGHT;
        }
        else{
            mPropLoc = PROP_LOC.NONE;
        }

        if(mPropLoc != PROP_LOC.NONE){
            mRobotState = ROBOT_STATE.TO_DROP;
        }

    }
    public void dropPixel(){
        mRobot.setIntakeSpeed(-0.2);
        sleep(500);
        mRobot.setIntakeSpeed(0);
        mRobotState = RedLeft2.ROBOT_STATE.RETURN_AND_PARK;
    }

    private void toDrop(){
        TrajectorySequence toDrop = mDropPixelSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toDrop.start());
        mRobot.autoDrive.followTrajectorySequence(toDrop);
        mIsRoadRunning = true;
    }

    private void toScore(){
        TrajectorySequence toScore = mOffToScoreSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toScore.start());
        mRobot.autoDrive.followTrajectorySequence(toScore);
        mIsRoadRunning = true;
    }

    private void scoreOnBoard(){

    }

    private void returnAndPark(){
        TrajectorySequence toPark = mReturnAndParkSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toPark.start());
        mRobot.autoDrive.followTrajectorySequence(toPark);
        mIsRoadRunning = true;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        mPipeline = new SignalDetector(hardwareMap, telemetry, true);
        mRobot = new Robot(telemetry, hardwareMap, false);

        mDropPixelSequences = new ArrayDeque<TrajectorySequence>();
        setupDropSequences();
        mOffToScoreSequences = new ArrayDeque<TrajectorySequence>();
        setupScoringSequences();
        mReturnAndParkSequences = new ArrayDeque<TrajectorySequence>();
        setupParkSequences();

        mPropLoc = PROP_LOC.NONE;
        mRobotState = ROBOT_STATE.SEE;
        ElapsedTime timer = new ElapsedTime();


        telemetry.addLine("Here we go");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;



        ///////////////GAME START////////////////////////////////

        while (opModeIsActive()){

            if(mIsRoadRunning){
                if(!mRobot.autoDrive.isBusy()){
                    switch(mRobotState){
                        case TO_DROP:
                            mRobotState = ROBOT_STATE.DROP;
                            break;
                        case GO_TO_SCORE:
                            mRobotState = ROBOT_STATE.SCORE;
                        case RETURN_AND_PARK:
                            mRobotState = ROBOT_STATE.IDLE;
                            break;
                    }
                    mIsRoadRunning = false;
                }
            }
            else {

                switch (mRobotState) {
                    case SEE:
                        detectObject();
                        break;
                    case TO_DROP:
                        toDrop();
                        break;
                    case DROP:
                        dropPixel();
                        break;
                    case GO_TO_SCORE:
                        toScore();
                        break;
                    case SCORE:

                    case RETURN_AND_PARK:
                        returnAndPark();
                        break;
                    case IDLE:
                        mRobot.autoDrive.setMotorPowers(0,0,0,0);

                }
            }

            telemetry.addLine("Prop Location: " + mPipeline.getRedPropLocation());
            telemetry.addLine("Robot State: " + mRobotState);
            telemetry.addLine("Is Roadrunner Running: " + mIsRoadRunning);
            telemetry.addLine("Current Time: " + timer.seconds());
            telemetry.update();


        }


    }

}
