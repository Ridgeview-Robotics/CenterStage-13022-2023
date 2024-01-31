package org.firstinspires.ftc.teamcode.Systems.General.Autos.Stage1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.Robot;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;

import kotlin.collections.ArrayDeque;

@Autonomous
@Disabled
public class Stage1Autonomous extends LinearOpMode {

    private enum ROBOT_STATE{
        SEE,
        TO_DROP,
        RUNNING,
        DROP,
        RETURN_AND_PARK,
        IDLE


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

    private Stage1Autonomous.PROP_LOC mPropLoc;
    private SignalDetector mPipeline;
    Robot mRobot;

    Stage1Autonomous.ROBOT_STATE mRobotState;
    boolean mIsRoadRunning;
    List<TrajectorySequence> mDropPixelSequences;
    List <TrajectorySequence> mReturnAndParkSequences;


    ///SEQUENCES HERE///

    private void detectObject(){
        sleep(100);
        String loc = mPipeline.getRedPropLocation();
        if (loc == "Left"){
            mPropLoc = Stage1Autonomous.PROP_LOC.LEFT;
        }
        else if(loc == "Center"){
            mPropLoc = Stage1Autonomous.PROP_LOC.CENTER;
        }
        else if(loc == "Right"){
            mPropLoc = Stage1Autonomous.PROP_LOC.RIGHT;
        }
        else{
            mPropLoc = Stage1Autonomous.PROP_LOC.NONE;
        }

        if(mPropLoc != Stage1Autonomous.PROP_LOC.NONE){
            mRobotState = Stage1Autonomous.ROBOT_STATE.TO_DROP;
        }

    }
    public void dropPixel(){
        mRobot.setIntakeSpeed(-0.2);
        sleep(500);
        mRobot.setIntakeSpeed(0);
        mRobotState = Stage1Autonomous.ROBOT_STATE.RETURN_AND_PARK;
    }

    private void toDrop(){
        TrajectorySequence toDrop = mDropPixelSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toDrop.start());
        mRobot.autoDrive.followTrajectorySequence(toDrop);
        mIsRoadRunning = true;
    }

    private void returnAndPark(){
        TrajectorySequence toPark = mReturnAndParkSequences.get(mPropLoc.getLocation());
        mRobot.autoDrive.setPoseEstimate(toPark.start());
        mRobot.autoDrive.followTrajectorySequence(toPark);
        mIsRoadRunning = true;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        mPipeline = new SignalDetector(hardwareMap, telemetry, true);
        mRobot = new Robot(telemetry, hardwareMap, false);

        mDropPixelSequences = new ArrayDeque<TrajectorySequence>();

        mReturnAndParkSequences = new ArrayDeque<TrajectorySequence>();


        mPropLoc = Stage1Autonomous.PROP_LOC.NONE;
        mRobotState = Stage1Autonomous.ROBOT_STATE.SEE;
        ElapsedTime timer = new ElapsedTime();


        telemetry.addLine("Here we go");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;



        ///////////////GAME START////////////////////////////////

        while (opModeIsActive()){

            if(mIsRoadRunning){
                if(!mRobot.autoDrive.isBusy()){
                    switch(mRobotState){
                        case TO_DROP:
                            mRobotState = Stage1Autonomous.ROBOT_STATE.DROP;
                            break;
                        case RETURN_AND_PARK:
                            mRobotState = Stage1Autonomous.ROBOT_STATE.IDLE;
                            break;
                    }
                    mIsRoadRunning = false;
                }
            }
            else {

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
                    case RETURN_AND_PARK:
                        returnAndPark();
                        break;
                    case IDLE:
                        mRobot.autoDrive.setMotorPowers(0,0,0,0);

                }
            }

            telemetry.addLine("Prop Location: " + mPipeline.getRedPropLocation());
            telemetry.addLine("Robot State: " + mRobotState);
            telemetry.addLine("Is Roadrunner Running: " + mIsRoadRunning);
            telemetry.addLine("Current Time: " + timer.seconds());
            telemetry.update();


        }


    }
}
