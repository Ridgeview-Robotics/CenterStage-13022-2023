package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage1;

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

@Autonomous(name = "BlueLeft")
public class BlueLeft1 extends LinearOpMode {

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

    public Robot mRobot;
    ROBOT_STATE mRobotState;
    TrajectorySequence mTS;

    private void setupSequence()
    {
        //Building Traj sequence

        //This is the starting position
        TrajectorySequenceBuilder rightRedSBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, 63.40, Math.toRadians(90.00)));

        //Vision Section
        if(mPropLoc == PROP_LOC.LEFT)
        {
            rightRedSBuilder.splineTo(new Vector2d(20.09, 43.23), Math.toRadians(-69.08));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            rightRedSBuilder.splineTo(new Vector2d(8.38, 33.48), Math.toRadians(-89.44));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(14.76, 42.93, Math.toRadians(219.51)));
            rightRedSBuilder.splineTo(new Vector2d(7.53, 38.11), Math.toRadians(219.88));
        }

        //Spit the pixel out
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(-0.2);
        });

        rightRedSBuilder.waitSeconds(0.5);

        //Stop the intake flippers
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        rightRedSBuilder.lineToLinearHeading(new Pose2d(11.67, -47.72, Math.toRadians(0.0)));

        //Building the Trajectory
        mTS = rightRedSBuilder.build();
    }

    private void detectObject()
    {
        sleep(100);
        String loc = mPipeline.getRedPropLocation();  //(Or mPipeline.getBluePropLocation();
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
            //mRobotState = ROBOT_STATE.SCORING;
        }
        telemetry.addLine("Loc: " + mPropLoc);
        telemetry.update();
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




        waitForStart();

        if (isStopRequested()) return;

        ///////////////GAME START////////////////////////////////

        while (opModeIsActive())
        {
            switch (mRobotState)
            {
                case SEE:
                    while(mPropLoc == PROP_LOC.NONE)
                        detectObject();

                    setupSequence();
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

    ///SEQUENCES HERE///
    /*private void setupDropSequences(){
        TrajectorySequence leftToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(20.09, 43.23), Math.toRadians(-69.08))
                .build();
        mDropPixelSequences.add(leftToPixel);

        TrajectorySequence centerToPixel = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(270.00)))
                .splineTo(new Vector2d(8.38, 33.48), Math.toRadians(-89.44))
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
                .lineTo(new Vector2d(17.28, 54.35))
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

    }*/

}
