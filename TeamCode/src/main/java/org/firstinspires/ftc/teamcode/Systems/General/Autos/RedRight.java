package org.firstinspires.ftc.teamcode.Systems.General.Autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;

import kotlin.collections.ArrayDeque;

@Autonomous(name= "Signal Finders")
public class RedRight extends LinearOpMode {
    public enum ROBOT_STATE{
        SEE,
        TO_DROP,
        RUNNING,
        DROP,
        RETURN,
        TO_PARKR,
        TO_PARKC,
        TO_PARKL
    }


    public enum PROP_LOC{
        LEFT(0),
        CENTER(1),
        RIGHT(2),
        NONE(3);

        private final int value;

        PROP_LOC(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
    }

    private PROP_LOC mPropLoc;
    private SignalDetector mPipeline;
    Robot mRobot;
    ROBOT_STATE mRobotState;
    boolean mIsRoadRunning;
    List<TrajectorySequence> mDropPixelSequences;

    public RedRight()
    {
        mDropPixelSequences = new ArrayDeque<TrajectorySequence>();
        mPropLoc    = PROP_LOC.NONE;
    }
    private void setupDropSequences()
    {
        TrajectorySequence leftSignalPath1 = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(10.75, -62.81, Math.toRadians(90.00)))
                .splineTo(new Vector2d(13.57, -41.60), Math.toRadians(82.43))
                .splineTo(new Vector2d(6.01, -33.00), Math.toRadians(131.33))
                .build();
        mDropPixelSequences.add(leftSignalPath1);

        TrajectorySequence centerSignalPath1 = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(10.16, -63.25, Math.toRadians(90.00)))
                .splineTo(new Vector2d(10.75, -35.07), Math.toRadians(88.79))
                .build();
        mDropPixelSequences.add(centerSignalPath1);

        TrajectorySequence rightSignalPathPart1 = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(9.71, -63.25, Math.toRadians(90.00)))
                .splineTo(new Vector2d(20.69, -38.63), Math.toRadians(65.97))
                .build();
        mDropPixelSequences.add(rightSignalPathPart1);
    }
    private void detectObject(){
        String loc = mPipeline.getPropLocation();
        if (loc == "Left")
            mPropLoc = PROP_LOC.LEFT;
        else if(loc == "Center")
            mPropLoc = PROP_LOC.CENTER;
        else if(loc == "Right")
            mPropLoc = PROP_LOC.RIGHT;
        else
            mPropLoc = PROP_LOC.NONE;

        if(mPropLoc != PROP_LOC.NONE)
            mRobotState = TO_DROP;
    }
    public void dropPixel(){
        mRobot.setIntakeSpeed(0.5);
        sleep(500);
        mRobotState = ROBOT_STATE.RETURN;
    }
    private void toDrop(){
        mRobot.autoDrive.setPoseEstimate(mDropPixelSequences.get(mPropLoc.getValue()).start());
        mIsRoadRunning = true;
    }

    private void toParkL(){
        TrajectorySequence leftSignalPath2 = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(6.01, -33.00, Math.toRadians(131.33)))
                .splineTo(new Vector2d(16.24, -43.97), Math.toRadians(144.46))
                .splineTo(new Vector2d(57.47, -58.21), Math.toRadians(-29.07))
                .build();
        mRobot.autoDrive.setPoseEstimate(leftSignalPath2.start());
    }
    private void toParkC(){
        TrajectorySequence centerSignalPath2 = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(10.75, -35.07, Math.toRadians(88.79)))
                .splineTo(new Vector2d(16.68, -48.57), Math.toRadians(94.86))
                .splineTo(new Vector2d(51.83, -61.03), Math.toRadians(-19.52))
                .build();
        mRobot.autoDrive.setPoseEstimate(centerSignalPath2.start());
    }
    private void toParkR(){
        TrajectorySequence rightSignalPathPart2 = mRobot.autoDrive.trajectorySequenceBuilder(new Pose2d(20.69, -38.63, Math.toRadians(65.97)))
                .splineTo(new Vector2d(21.43, -48.87), Math.toRadians(11.77))
                .splineTo(new Vector2d(52.42, -56.43), Math.toRadians(-13.71))
                .build();
        mRobot.autoDrive.setPoseEstimate(rightSignalPathPart2.start());
    }
    @Override
    public void runOpMode() throws InterruptedException {

        mPipeline       = new SignalDetector(hardwareMap, telemetry);
        mRobot          = new Robot(telemetry, hardwareMap, false);
        mIsRoadRunning  = false;
        mRobotState     = ROBOT_STATE.SEE;

        telemetry.addLine("Here we go");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        ///////////////GAME START////////////////////////////////

        while (opModeIsActive())
        {
            if(mIsRoadRunning)
            {
                if(!mRobot.autoDrive.isBusy())
                {
                    switch(mRobotState){
                        case TO_DROP:
                            mRobotState = ROBOT_STATE.DROP;
                            break;
                    }
                    mIsRoadRunning = false;
                }
            }
            else
            {
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
                    case TO_PARKL:
                        toParkL();
                        break;
                    case TO_PARKC:
                        toParkC();
                        break;
                    case TO_PARKR:
                        toParkR();
                        break;

                }
            }

            telemetry.addLine("Prop Location: " + mPipeline.getPropLocation());
            telemetry.addLine("Robot State: " + mRobotState);
            telemetry.addLine("Is Roadrunner Running: " + mIsRoadRunning);
            telemetry.update();
        }

    }
}
