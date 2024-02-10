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
@Autonomous(name = "BlueRight(st1)")
public class BlueRight1 extends LinearOpMode {

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
        TrajectorySequenceBuilder blueRightBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(-36.00, 64.00, Math.toRadians(270.00)));

        //Vision Section
        if(mPropLoc == PROP_LOC.LEFT)
        {
           blueRightBuilder.lineToLinearHeading(new Pose2d(-38.93, 43.92, Math.toRadians(-41.78)));
           blueRightBuilder.splineTo(new Vector2d(-34.08, 39.28), Math.toRadians(-38.29));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            blueRightBuilder.splineTo(new Vector2d(-40.26, 36.92), Math.toRadians(270.00));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            blueRightBuilder.lineToLinearHeading(new Pose2d(-48.09, 42.30, Math.toRadians(-89.14)));

        }

        //Spit the pixel out
        blueRightBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(-0.2);
        });

        blueRightBuilder.waitSeconds(0.5);

        //Stop the intake flippers
        blueRightBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        blueRightBuilder.lineToLinearHeading(new Pose2d(-36.00, 64.00, Math.toRadians(270.0)));

        blueRightBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setYawDown();
        });

        //Building the Trajectory
        mTS = blueRightBuilder.build();
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
        mPropLoc    = PROP_LOC.RIGHT;
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
