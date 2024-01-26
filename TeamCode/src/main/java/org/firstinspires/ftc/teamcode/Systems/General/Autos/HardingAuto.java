package org.firstinspires.ftc.teamcode.Systems.General.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

import java.util.List;

import kotlin.collections.ArrayDeque;

@Autonomous(name= "RedRight")
public class HardingAuto extends LinearOpMode {
    private enum ROBOT_STATE{
        SEE,
        SCORING,
        AUTO_END
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
    TrajectorySequence mTS;

    private void setupSequence()
    {
        TrajectorySequenceBuilder rightRedSBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)));

        if(mPropLoc == PROP_LOC.LEFT)
        {
            rightRedSBuilder.splineTo(new Vector2d(23.80, -44.56), Math.toRadians(65.61));
            rightRedSBuilder.splineTo(new Vector2d(9.12, -38.19), Math.toRadians(149.37));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            rightRedSBuilder.splineTo(new Vector2d(13.57, -35.81), Math.toRadians(83.21));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            rightRedSBuilder.splineTo(new Vector2d(-44.27, -47.68), Math.toRadians(93.69));
            rightRedSBuilder.splineTo(new Vector2d(-31.96, -39.37), Math.toRadians(33.23));
        }

        //Spit the pixel out
        rightRedSBuilder.addDisplacementMarker(() -> {
            mRobot.setIntakeSpeed(-0.2);
        });

        rightRedSBuilder.waitSeconds(.5);

        //Stop the intake flippers
        rightRedSBuilder.addDisplacementMarker(() -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        rightRedSBuilder.lineToLinearHeading(new Pose2d(11.67, -47.72, Math.toRadians(0.00)));

        //Move to the front of the score board
        rightRedSBuilder.lineToLinearHeading(new Pose2d(50.89, -36.51, Math.toRadians(0.00)));

        //Rotate yaw
        rightRedSBuilder.addDisplacementMarker(() -> {
            mRobot.setYawTarget(10);
        });

        rightRedSBuilder.waitSeconds(.5);

        //Raise arm
        rightRedSBuilder.addDisplacementMarker(() -> {
            mRobot.setOutboardTarget(10);
        });

        rightRedSBuilder.waitSeconds(.5);

        //Prep wrist to score
        rightRedSBuilder.addDisplacementMarker(() -> {
            mRobot.setBoxScore();
        });

        rightRedSBuilder.waitSeconds(.5);

        //Open trap door to score
        rightRedSBuilder.addDisplacementMarker(() -> {
            mRobot.setTrapdoorOpen();
        });

        mTS = rightRedSBuilder.build();
    }

    private void detectObject()
    {
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
            mRobotState = ROBOT_STATE.SCORING;
        }
    }

    private void initializeOpMode()
    {
        mPipeline   = new SignalDetector(hardwareMap, telemetry, true);
        mRobot      = new Robot(telemetry, hardwareMap, false);
        mPropLoc    = PROP_LOC.NONE;
        mRobotState = ROBOT_STATE.SEE;
    }
    @Override
    public void runOpMode() throws InterruptedException
    {
        initializeOpMode();
        ElapsedTime timer = new ElapsedTime();

        telemetry.addLine("Here we go");
        telemetry.update();

        while(mPropLoc == PROP_LOC.NONE)
            detectObject();

        setupSequence();
        mRobotState = ROBOT_STATE.SCORING;

        waitForStart();

        if (isStopRequested()) return;

        ///////////////GAME START////////////////////////////////

        while (opModeIsActive())
        {
            switch (mRobotState)
            {
                case SCORING:
                    //startToScore
                    mRobot.autoDrive.setPoseEstimate(mTS.start());
                    mRobot.autoDrive.followTrajectorySequence(mTS);
                    mRobotState = ROBOT_STATE.AUTO_END;
                    break;
                case AUTO_END:
                    //We are done!
                    break;
            }
            telemetry.addLine("Prop Location: " + mPipeline.getRedPropLocation());
            telemetry.addLine("Robot State: " + mRobotState);
            telemetry.addLine("Current Time: " + timer.seconds());
            telemetry.update();
        }


    }

}

