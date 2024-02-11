package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage2;

import com.acmerobotics.dashboard.config.Config;
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

@Config
@Autonomous(name = "RedRight(st2)")
public class RedRight2 extends LinearOpMode {

    public static double leftPixelX = 6.86;
    public static double leftPixelY = -40.86;
    public static double centerPixelX = 9.57;
    public static double centerPixelY = -37.81;
    public static double rightPixelX = 19.50;
    public static double rightPixelY = -45.87;
    public static double boardX = 51.2;
    public static double rightBoardY = -39.6;
    public static double centerBoardY  = -35.00;
    public static double leftBoardY = -28.30;
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
        TrajectorySequenceBuilder rightRedSBuilder = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(11.49, -63.40, Math.toRadians(95.00)));

        if(mPropLoc == PROP_LOC.LEFT)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(14.01, -44.71, Math.toRadians(137.15)));
            rightRedSBuilder.splineTo(new Vector2d(leftPixelX, leftPixelY), Math.toRadians(137.35));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            rightRedSBuilder.splineTo(new Vector2d(centerPixelX, centerPixelY), Math.toRadians(83.21));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            rightRedSBuilder.splineTo(new Vector2d(rightPixelX, centerPixelY), Math.toRadians(88.95));

        }

        //Spit the pixel out
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(-0.2);
            mRobot.setTrapdoorClosed();
        });

        rightRedSBuilder.waitSeconds(0.45);

        //Stop the intake flippers
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setIntakeSpeed(0);
        });

        //Move backwards to clear the bot of any pixel that has been placed
        rightRedSBuilder.lineTo(new Vector2d(21.67, -51.72));

        //Rotate yaw
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setYawScore();
        });

        TrajectoryAccelerationConstraint trajConstraint = new ProfileAccelerationConstraint(15);
        rightRedSBuilder.setAccelConstraint(trajConstraint);

        //Move to the front of the score board
        if(mPropLoc == PROP_LOC.LEFT)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(boardX, leftBoardY, Math.toRadians(180.0)));
            rightRedSBuilder.waitSeconds(0.3);
            rightRedSBuilder.lineToLinearHeading(new Pose2d(boardX+2, leftBoardY, Math.toRadians(180.0)));
        }
        else if(mPropLoc == PROP_LOC.CENTER)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(boardX, centerBoardY, Math.toRadians(180.0)));
            rightRedSBuilder.waitSeconds(0.3);
            rightRedSBuilder.lineToLinearHeading(new Pose2d(boardX+2, centerBoardY, Math.toRadians(180.0)));
        }
        else if(mPropLoc == PROP_LOC.RIGHT)
        {
            rightRedSBuilder.lineToLinearHeading(new Pose2d(boardX, rightBoardY, Math.toRadians(180.0)));
            rightRedSBuilder.waitSeconds(0.3);
            rightRedSBuilder.lineToLinearHeading(new Pose2d(boardX+2, rightBoardY, Math.toRadians(180.0)));
        }



        //Raise arm
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setBoxScore();
        });

        rightRedSBuilder.waitSeconds(0.875);

        //Open trap door to score
        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () -> {
            mRobot.setTrapdoorOpen();
        });

        rightRedSBuilder.waitSeconds(0.45);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setBoxIntake();
            mRobot.setOutboardRetracted();
        });

        rightRedSBuilder.waitSeconds(0.1);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.lift.setYawClearance();
        });

        rightRedSBuilder.waitSeconds(0.35);

        rightRedSBuilder.lineToLinearHeading(new Pose2d(52.5, -40.51, Math.toRadians(180.00)));
        //drive out to get to parking line
        rightRedSBuilder.lineTo(new Vector2d(50.00, -63.00));

        rightRedSBuilder.resetAccelConstraint();
        //line to get to parking
        rightRedSBuilder.lineTo(new Vector2d(63.00, -63.00));

        rightRedSBuilder.waitSeconds(0.2);

        rightRedSBuilder.UNSTABLE_addTemporalMarkerOffset(0, () ->{
            mRobot.setYawDown();
        });

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
