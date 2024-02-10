package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage2;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

@Autonomous(name = "Blue Left(st2)")
public class BlueLeft2 extends LinearOpMode {
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
        TrajectorySequenceBuilder blueLeftSequenceBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, 63.40, Math.toRadians(270.00)));

        if(mPropLoc == PROP_LOC.LEFT)
        {
            blueLeftSequenceBuilder.splineTo(new Vector2d(17.09, 43.23), Math.toRadians(-69.08));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            blueLeftSequenceBuilder.splineTo(new Vector2d(8.38, 37.00), Math.toRadians(-89.44));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(14.76, 42.93, Math.toRadians(219.51)));
            blueLeftSequenceBuilder.splineTo(new Vector2d(7.53, 38.11), Math.toRadians(219.88));
        }

        //Spit the pixel out
        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(-0.2);
            mRobot.setTrapdoorClosed();
        });

        blueLeftSequenceBuilder.waitSeconds(0.45);

        //Stop the intake flippers
        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        blueLeftSequenceBuilder.lineTo(new Vector2d(21.67, 51.72));

        //Rotate yaw
        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setYawScore();
        });

        blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(36.96, 51.35, Math.toRadians(180.0)));

        //Move to the front of the score board
        if(mPropLoc == PROP_LOC.LEFT)
        {
            blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(51.5, 41.6, Math.toRadians(180.0)));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(51.2, 37.51, Math.toRadians(180.0)));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(42.41, 38.81, Math.toRadians(180.0)));
            blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(51.2, 27.50, Math.toRadians(180.0)));
        }

        blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(53.0, 37.51, Math.toRadians(180.0)));

        //Raise arm
        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setBoxScore();
        });

        blueLeftSequenceBuilder.waitSeconds(0.875);

        //Open trap door to score
        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setTrapdoorOpen();
        });

        blueLeftSequenceBuilder.waitSeconds(0.45);

        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setBoxIntake();
            mRobot.setOutboardRetracted();
        });

        blueLeftSequenceBuilder.waitSeconds(0.1);

        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.lift.setYawClearance();
        });

        blueLeftSequenceBuilder.lineToLinearHeading(new Pose2d(52.5, 40.51, Math.toRadians(180.00)));
        //drive out to get to parking line
        blueLeftSequenceBuilder.lineTo(new Vector2d(50.00, 63.00));



        //line to get to parking
        blueLeftSequenceBuilder.lineTo(new Vector2d(63.00, 63.00));

        blueLeftSequenceBuilder.waitSeconds(0.2);

        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setYawDown();
        });

        blueLeftSequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobotState = ROBOT_STATE.AUTO_END;
                });


        mTS = blueLeftSequenceBuilder.build();
    }

    private void detectObject()
    {
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
        mRobot.liftWithClearanceCheck(CombineLiftC.outboardPositions.DOWN, CombineLiftC.yawPositions.CLEAR, CombineLiftC.yawPositions.CLEAR);
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
}
