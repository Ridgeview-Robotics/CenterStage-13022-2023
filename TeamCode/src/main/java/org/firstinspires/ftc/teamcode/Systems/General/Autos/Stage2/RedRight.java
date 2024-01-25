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

@Autonomous(name= "RedRight(st2)")
public class RedRight extends LinearOpMode {
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
    List <TrajectorySequence> mReturnAndScoreSequences;
    List <TrajectorySequence> mOffToParkSequences;


    private void setupDropSequences()
    {
        TrajectorySequence leftToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)))
                .splineTo(new Vector2d(23.80, -44.56), Math.toRadians(65.61))
                .splineTo(new Vector2d(9.12, -38.19), Math.toRadians(149.37))
                .build();
        mDropPixelSequences.add(leftToPixel);

        TrajectorySequence centerToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)))
                .splineTo(new Vector2d(13.57, -35.81), Math.toRadians(83.21))
                .build();
        mDropPixelSequences.add(centerToPixel);


        TrajectorySequence rightToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)))
                .splineTo(new Vector2d(23.06, -44.12), Math.toRadians(90.00))
                .build();
        mDropPixelSequences.add(rightToPixel);
    }

    private void setupScoreSequences(){
        TrajectorySequence leftToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)))
                .lineTo(new Vector2d(23.06, -51.39))
                .splineTo(new Vector2d(50.94, -31.81), Math.toRadians(180.00))
                .build();
        mReturnAndScoreSequences.add(leftToScore);

        TrajectorySequence centerToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(13.57, -35.81, Math.toRadians(83.21)))
                .lineTo(new Vector2d(20.98, -45.90))
                .splineTo(new Vector2d(51.09, -37.00), Math.toRadians(180.00))
                .build();
        mReturnAndScoreSequences.add(centerToScore);

        TrajectorySequence rightToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(23.06, -44.12, Math.toRadians(90.00)))
                .lineTo(new Vector2d(18.17, -50.50))
                .splineTo(new Vector2d(50.64, -42.49), Math.toRadians(180.00))
                .build();
        mReturnAndScoreSequences.add(rightToScore);

    }

    private void setupParkingSequences(){
        TrajectorySequence leftToParking = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.94, -31.81, Math.toRadians(180.00)))
                .lineTo(new Vector2d(27.95, -61.32))
                .lineTo(new Vector2d(58.21, -61.17))
                .build();
        mOffToParkSequences.add(leftToParking);

        TrajectorySequence centerToParking = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.35, -36.41, Math.toRadians(180.00)))
                .lineTo(new Vector2d(27.95, -61.32))
                .lineTo(new Vector2d(58.21, -61.17))
                .build();
        mOffToParkSequences.add(centerToParking);

        TrajectorySequence rightToParking = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.64, -42.49, Math.toRadians(180.00)))
                .lineTo(new Vector2d(27.95, -61.32))
                .lineTo(new Vector2d(58.21, -61.17))
                .build();
        mOffToParkSequences.add(rightToParking);
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
        mRobotState = ROBOT_STATE.GO_TO_SCORE;
    }

    private void toDrop(){
        TrajectorySequence toDrop = mDropPixelSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toDrop.start());
        mRobot.autoDrive.followTrajectorySequence(toDrop);
        mIsRoadRunning = true;
    }

    private void score(){ //input while loop
        sleep(1000);
        mRobotState = ROBOT_STATE.RETURN_AND_PARK;
    }

    private void returnAndScore(){
        TrajectorySequence toScore = mReturnAndScoreSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toScore.start());
        mRobot.autoDrive.followTrajectorySequence(toScore);
        mIsRoadRunning = true;
    }

    private void returnToParking(){
        TrajectorySequence toPark = mOffToParkSequences.get(mPropLoc.getLocation());
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
        mReturnAndScoreSequences = new ArrayDeque<TrajectorySequence>();
        setupScoreSequences();
        mOffToParkSequences = new ArrayDeque<TrajectorySequence>();
        setupParkingSequences();

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
                            break;
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
                        returnAndScore();
                        break;
                    case SCORE:
                        score();
                        break;
                    case RETURN_AND_PARK:
                        returnToParking();
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
