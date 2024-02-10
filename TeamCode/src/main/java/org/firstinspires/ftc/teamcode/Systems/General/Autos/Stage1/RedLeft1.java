package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage1;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;

import java.util.List;

import kotlin.collections.ArrayDeque;

@Autonomous(name = "Red Left(st1)")
public class RedLeft1 extends LinearOpMode {

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
        TrajectorySequenceBuilder rightRedSBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)));

        if(mPropLoc == PROP_LOC.LEFT)
        {
            rightRedSBuilder.splineTo(new Vector2d(-40.30, -40.50), Math.toRadians(122.30));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            rightRedSBuilder.splineTo(new Vector2d(-35.59, -36.74), Math.toRadians(90.0));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(-39.52, -54.50, Math.toRadians(75.20)));
            rightRedSBuilder.lineToLinearHeading(new Pose2d(-30.77, -39.82, Math.toRadians(56.82)));

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
        rightRedSBuilder.lineToLinearHeading(new Pose2d(-36.00, -63.00, Math.toRadians(90.00)));

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
           mRobot.setYawDown();
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
        mPropLoc    = PROP_LOC.LEFT;
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
