package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage3;

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

@Autonomous(name = "RedRight(st3)")
public class RedRight3 extends LinearOpMode {
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
        TrajectorySequenceBuilder rightRedSBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(90.00)));

        if(mPropLoc == PROP_LOC.LEFT)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(14.01, -44.71, Math.toRadians(137.15)));
            rightRedSBuilder.splineTo(new Vector2d(9.86, -40.86), Math.toRadians(137.35));
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
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(-0.2);
            mRobot.setTrapdoorClosed();
        });

        rightRedSBuilder.waitSeconds(0.5);

        //Stop the intake flippers
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        rightRedSBuilder.lineToLinearHeading(new Pose2d(11.67, -48.72, Math.toRadians(0.0)));

        //Rotate yaw
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setYawScore();
        });

        //Move to the front of the score board
        rightRedSBuilder.lineToLinearHeading(new Pose2d(50.89, -36.51, Math.toRadians(180.0)));

        //Raise arm
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setOutboardFirstLine();
            mRobot.setBoxScore();
        });

        rightRedSBuilder.waitSeconds(0.5);

        //Open trap door to score
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setTrapdoorOpen();
        });

        rightRedSBuilder.waitSeconds(1.5);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setBoxIntake();
            mRobot.setOutboardRetracted();
        });

        rightRedSBuilder.waitSeconds(0.5);

        //Line away from Drop Board
        rightRedSBuilder.lineToLinearHeading(new Pose2d(34.63, -12.72, Math.toRadians(180.00)));

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{

            mRobot.setYawDown();
        });

        //Line to Pixel line
        rightRedSBuilder.lineTo(new Vector2d(-59.99, -12.35));

       rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    mRobot.setIntakeSpeed(1.0);
        });

       rightRedSBuilder.waitSeconds(0.5);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset( 0,() ->{
            mRobot.setIntakeSpeed(0.0);
        });

        //drive back toward board
        rightRedSBuilder.lineToLinearHeading(new Pose2d(34.63, -3.00, Math.toRadians(180.00)));

        //raise yaw
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setYawScore();
        });

        //stop the burnouts
        TrajectoryAccelerationConstraint trajConstraint = new ProfileAccelerationConstraint(25);
        rightRedSBuilder.setAccelConstraint(trajConstraint);

        //drive to actual board
        rightRedSBuilder.lineToLinearHeading(new Pose2d(50.89, -36.51, Math.toRadians(180.00)));



        rightRedSBuilder.resetAccelConstraint();

        //INSERT SCORING HERE  DONE

        //Raise arm
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setOutboardFirstLine();
        });

        rightRedSBuilder.waitSeconds(0.5);

        //Prep wrist to score
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setBoxScore();
        });

        rightRedSBuilder.waitSeconds(0.5);

        //Open trap door to score
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setTrapdoorOpen();
        });

        rightRedSBuilder.waitSeconds(1.5);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
           mRobot.setBoxIntake();
           mRobot.setOutboardRetracted();
        });

        rightRedSBuilder.waitSeconds(0.5);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setYawDown();
            });


        //drive out to get to parking line
        rightRedSBuilder.lineTo(new Vector2d(52.00, -63.00));

        //line to get to parking
        rightRedSBuilder.lineTo(new Vector2d(63.00, -63.00));

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobotState = ROBOT_STATE.AUTO_END;
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
