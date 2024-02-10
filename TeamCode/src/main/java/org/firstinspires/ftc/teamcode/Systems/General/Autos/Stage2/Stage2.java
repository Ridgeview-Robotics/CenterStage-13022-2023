package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage2;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

@Disabled
@Autonomous(name = "Name")
public class Stage2 extends LinearOpMode {
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
        TrajectorySequenceBuilder SequenceBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)));

        if(mPropLoc == PROP_LOC.LEFT)
        {
            SequenceBuilder.lineToLinearHeading(new Pose2d(14.01, -44.71, Math.toRadians(137.15)));
            SequenceBuilder.splineTo(new Vector2d(9.86, -40.86), Math.toRadians(137.35));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            SequenceBuilder.splineTo(new Vector2d(13.57, -33.81), Math.toRadians(83.21));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            SequenceBuilder.splineTo(new Vector2d(22.50, -45.87), Math.toRadians(88.95));

        }

        //Spit the pixel out
        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(-0.2);
            mRobot.setTrapdoorClosed();
        });

        SequenceBuilder.waitSeconds(0.5);

        //Stop the intake flippers
        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        SequenceBuilder.lineTo(new Vector2d(21.67, -51.72));

        //Rotate yaw
        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setYawScore();
        });

        //Move to the front of the score board
        if(mPropLoc == PROP_LOC.LEFT)
        {
            SequenceBuilder.lineToLinearHeading(new Pose2d(51.5, -31.50, Math.toRadians(180.0)));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            SequenceBuilder.lineToLinearHeading(new Pose2d(51.2, -37.51, Math.toRadians(180.0)));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            SequenceBuilder.lineToLinearHeading(new Pose2d(51.2, -41.6, Math.toRadians(180.0)));
        }

        //Raise arm
        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setBoxScore();
        });

        SequenceBuilder.waitSeconds(1.0);

        //Open trap door to score
        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setTrapdoorOpen();
        });

        SequenceBuilder.waitSeconds(0.5);

        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setBoxIntake();
            mRobot.setOutboardRetracted();
        });

        SequenceBuilder.waitSeconds(0.2);

        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.lift.setYawClearance();
        });

        //drive out to get to parking line
        SequenceBuilder.lineTo(new Vector2d(52.00, -63.00));

        //line to get to parking
        SequenceBuilder.lineTo(new Vector2d(63.00, -63.00));

        SequenceBuilder.waitSeconds(0.2);

        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setYawDown();
        });

        SequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobotState = ROBOT_STATE.AUTO_END;
        });

        mTS = SequenceBuilder.build();
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
