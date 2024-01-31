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
@Autonomous(name = "Blue Right(st2)")
public class BlueRight2 extends LinearOpMode {

    private enum ROBOT_STATE{
        SEE,
        TO_DROP,
        RUNNING,
        DROP,
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
    List<TrajectorySequence> mOffToScoringSequences;
    List <TrajectorySequence> mReturnAndParkSequences;


    ///SEQUENCES HERE///

    private void setupDropSequences(){
        TrajectorySequence leftToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, 64.00, Math.toRadians(270)))
                .splineTo(new Vector2d(-39.37, 48.57), Math.toRadians(-79.76))
                .splineTo(new Vector2d(-32.55, 39.08), Math.toRadians(-24.32))
                .build();
        mDropPixelSequences.add(leftToPixel);

        TrajectorySequence centerToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, 64.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(-32.55, 34.18), Math.toRadians(268.19))
                .build();
        mDropPixelSequences.add(centerToPixel);

        TrajectorySequence rightToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, 64.00, Math.toRadians(-89.14)))
                .splineTo(new Vector2d(-44.56, 43.23), Math.toRadians(250.07))
                .build();
        mDropPixelSequences.add(rightToPixel);
    }

    private void setupParkSequences(){
        TrajectorySequence leftToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-32.55, 39.08, Math.toRadians(-24.32)))
                .lineTo(new Vector2d(-53.61, 36.85))
                .splineTo(new Vector2d(-44.56, 13.27), Math.toRadians(-69.50))
                .splineTo(new Vector2d(61.62, 12.53), Math.toRadians(0.00))
                .build();
        mReturnAndParkSequences.add(leftToPark);

        TrajectorySequence centerToBackStage = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-32.55, 34.18, Math.toRadians(268.19)))
                .lineTo(new Vector2d(-52.72, 38.78))
                .splineTo(new Vector2d(-53.61, 19.80), Math.toRadians(266.82))
                .splineTo(new Vector2d(61.32, 13.42), Math.toRadians(0.00))
                .build();
        mReturnAndParkSequences.add(centerToBackStage);

        TrajectorySequence rightToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-44.56, 43.23, Math.toRadians(250.07)))
                .lineTo(new Vector2d(-31.81, 39.08))
                .splineTo(new Vector2d(-38.34, 20.98), Math.toRadians(250.62))
                .splineTo(new Vector2d(61.77, 13.72), Math.toRadians(0.00))
                .build();
        mReturnAndParkSequences.add(rightToPark);

    }

    private void detectObject(){
        sleep(100);
        String loc = mPipeline.getBluePropLocation();
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
        mRobotState = ROBOT_STATE.RETURN_AND_PARK;
    }

    private void toDrop(){
        TrajectorySequence toDrop = mDropPixelSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toDrop.start());
        mRobot.autoDrive.followTrajectorySequence(toDrop);
        mIsRoadRunning = true;
    }

    private void returnAndPark(){
        TrajectorySequence toPark = mReturnAndParkSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toPark.start());
        mRobot.autoDrive.followTrajectorySequence(toPark);
        mIsRoadRunning = true;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        mPipeline = new SignalDetector(hardwareMap, telemetry, false);
        mRobot = new Robot(telemetry, hardwareMap, false);

        mDropPixelSequences = new ArrayDeque<TrajectorySequence>();
        setupDropSequences();
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

