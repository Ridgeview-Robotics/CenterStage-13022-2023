package org.firstinspires.ftc.teamcode.Systems.General.Autos;

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

@Autonomous(name = "BlueLeft")
public class BlueLeft extends LinearOpMode {
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

    private BlueLeft.PROP_LOC mPropLoc;
    private SignalDetector mPipeline;
    Robot mRobot;

    BlueLeft.ROBOT_STATE mRobotState;
    boolean mIsRoadRunning;
    List<TrajectorySequence> mDropPixelSequences;
    List <TrajectorySequence> mReturnAndParkSequences;


    ///SEQUENCES HERE///
    private void setupDropSequences(){
        TrajectorySequence leftToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(20.09, 43.23), Math.toRadians(-69.08))
                .build();
        mDropPixelSequences.add(leftToPixel);

        TrajectorySequence centerToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(8.38, 34.48), Math.toRadians(-89.44))
                .build();
        mDropPixelSequences.add(centerToPixel);

        TrajectorySequence rightToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(14.01, 50.94), Math.toRadians(-64.52))
                .splineTo(new Vector2d(7.93, 39.08), Math.toRadians(214.66))
                .build();
        mDropPixelSequences.add(rightToPixel);

    }

    private void setupParkSequences(){
        TrajectorySequence leftToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(20.09, 43.23, Math.toRadians(-69.08)))
                .lineTo(new Vector2d(58.95, 59.39))
                .build();
        mReturnAndParkSequences.add(leftToPark);

        TrajectorySequence centerToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(8.38, 34.48, Math.toRadians(-89.44)))
                .lineTo(new Vector2d(59.10, 62.21))
                .build();
        mReturnAndParkSequences.add(centerToPark);

        TrajectorySequence rightToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(7.93, 39.08, Math.toRadians(214.66)))
                .lineTo(new Vector2d(59.69, 60.28))
                .build();
        mReturnAndParkSequences.add(rightToPark);

    }

    private void detectObject(){
        sleep(100);
        String loc = mPipeline.getBluePropLocation();
        if (loc == "Left"){
            mPropLoc = BlueLeft.PROP_LOC.LEFT;
        }
        else if(loc == "Center"){
            mPropLoc = BlueLeft.PROP_LOC.CENTER;
        }
        else if(loc == "Right"){
            mPropLoc = BlueLeft.PROP_LOC.RIGHT;
        }
        else{
            mPropLoc = BlueLeft.PROP_LOC.NONE;
        }

        if(mPropLoc != BlueLeft.PROP_LOC.NONE){
            mRobotState = BlueLeft.ROBOT_STATE.TO_DROP;
        }

    }
    public void dropPixel(){
        mRobot.setIntakeSpeed(-0.2);
        sleep(500);
        mRobot.setIntakeSpeed(0);
        mRobotState = BlueLeft.ROBOT_STATE.RETURN_AND_PARK;
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

        mPropLoc = BlueLeft.PROP_LOC.NONE;
        mRobotState = BlueLeft.ROBOT_STATE.SEE;
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
                            mRobotState = BlueLeft.ROBOT_STATE.DROP;
                            break;
                        case RETURN_AND_PARK:
                            mRobotState = BlueLeft.ROBOT_STATE.IDLE;
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

            telemetry.addLine("Prop Location: " + mPipeline.getBluePropLocation());
            telemetry.addLine("Robot State: " + mRobotState);
            telemetry.addLine("Is Roadrunner Running: " + mIsRoadRunning);
            telemetry.addLine("Current Time: " + timer.seconds());
            telemetry.update();


        }


    }


}
