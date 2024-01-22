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

@Autonomous(name = "BlueLeft(st2)")
public class BlueLeft extends LinearOpMode {
    private enum ROBOT_STATE {
        SEE,
        TO_DROP,
        RUNNING,
        DROP,
        TO_SCORE,
        SCORE,
        RETURN_AND_PARK,
        IDLE


    }

    private enum PROP_LOC {
        LEFT(0),
        CENTER(1),
        RIGHT(2),
        NONE(3);

        private final int location;

        PROP_LOC(final int newLocation) {
            location = newLocation;
        }

        private int getLocation() {
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
    List<TrajectorySequence> mReturnAndParkSequences;


    ///SEQUENCES HERE///
    private void setupDropSequences() {
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

    private void setupScoringSequences(){
        TrajectorySequence leftToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(20.09, 43.23, Math.toRadians(-69.08)))
                .lineTo(new Vector2d(19.35, 60.28))
                .splineTo(new Vector2d(43.23, 59.54), Math.toRadians(180.00))
                .lineTo(new Vector2d(50.79, 42.04))
                .build();

        mOffToScoringSequences.add(leftToScore);

        TrajectorySequence centerToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(8.38, 34.48, Math.toRadians(-89.44)))
                .lineTo(new Vector2d(11.35, 60.88))
                .splineTo(new Vector2d(35.67, 61.62), Math.toRadians(180.00))
                .lineTo(new Vector2d(51.09, 36.11))
                .build();

        mOffToScoringSequences.add(centerToScore);

        TrajectorySequence rightToScore = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(7.93, 39.08, Math.toRadians(214.66)))
                .lineTo(new Vector2d(25.58, 58.50))
                .splineTo(new Vector2d(40.86, 45.31), Math.toRadians(180.00))
                .lineTo(new Vector2d(50.94, 35.67))
                .build();
        mOffToScoringSequences.add(rightToScore);
    }

    private void setupParkSequences() {
        TrajectorySequence leftToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.79, 42.04, Math.toRadians(180.00)))
                .lineTo(new Vector2d(48.27, 10.46))
                .lineTo(new Vector2d(61.47, 13.12))
                .build();
        mReturnAndParkSequences.add(leftToPark);

        TrajectorySequence centerToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(51.09, 36.11, Math.toRadians(180.00)))
                .lineTo(new Vector2d(43.97, 18.32))
                .lineTo(new Vector2d(61.03, 11.35))
                .build();
        mReturnAndParkSequences.add(centerToPark);

        TrajectorySequence rightToPark = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(50.94, 35.67, Math.toRadians(180.00)))
                .lineTo(new Vector2d(43.97, 18.32))
                .lineTo(new Vector2d(61.03, 11.35))
                .build();
        mReturnAndParkSequences.add(rightToPark);

    }

    private void detectObject() {
        sleep(100);
        String loc = mPipeline.getBluePropLocation();
        if (loc == "Left") {
            mPropLoc = PROP_LOC.LEFT;
        } else if (loc == "Center") {
            mPropLoc = PROP_LOC.CENTER;
        } else if (loc == "Right") {
            mPropLoc = PROP_LOC.RIGHT;
        } else {
            mPropLoc = PROP_LOC.NONE;
        }

        if (mPropLoc != PROP_LOC.NONE) {
            mRobotState = ROBOT_STATE.TO_DROP;
        }

    }

    public void dropPixel() {
        mRobot.setIntakeSpeed(-0.2);
        sleep(500);
        mRobot.setIntakeSpeed(0);
        mRobotState = ROBOT_STATE.RETURN_AND_PARK;
    }

    private void toDrop() {
        TrajectorySequence toDrop = mDropPixelSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toDrop.start());
        mRobot.autoDrive.followTrajectorySequence(toDrop);
        mIsRoadRunning = true;
    }

    private void toScore(){
        TrajectorySequence toScore = mOffToScoringSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toScore.start());
        mRobot.autoDrive.followTrajectorySequence(toScore);
        mIsRoadRunning = true;
    }

    private void score(){

    }

    private void returnAndPark() {
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
        mOffToScoringSequences = new ArrayDeque<TrajectorySequence>();
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

        while (opModeIsActive()) {

            if (mIsRoadRunning) {
                if (!mRobot.autoDrive.isBusy()) {
                    switch (mRobotState) {
                        case TO_DROP:
                            mRobotState = ROBOT_STATE.DROP;
                            break;
                        case TO_SCORE:
                            mRobotState = ROBOT_STATE.SCORE;
                            break;
                        case RETURN_AND_PARK:
                            mRobotState = ROBOT_STATE.IDLE;
                            break;
                    }
                    mIsRoadRunning = false;
                }
            } else {

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
                    case TO_SCORE:
                        toScore();
                        break;
                    case SCORE:
                        score();
                        break;
                    case RETURN_AND_PARK:
                        returnAndPark();
                        break;
                    case IDLE:
                        mRobot.autoDrive.setMotorPowers(0, 0, 0, 0);

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