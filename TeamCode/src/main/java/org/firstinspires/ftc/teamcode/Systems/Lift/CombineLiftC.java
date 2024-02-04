package org.firstinspires.ftc.teamcode.Systems.Lift;


import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class CombineLiftC extends BasicLift {

    public RREMX outboard;
    public RREMX yaw;

    private final TouchSensor touchSensor;

    private boolean calibrated = false;

    public static int yawDownPos = 0;
    public static int outboardRetractedPos = 0;
    public static final int yawClearPos = 900;
    public static final int yawScorePos = 1750;
    public static final int outboardFirstLinePos = 525;
    public static final int outboardMiddlePos = 1200;
    public static final int outboardHighestPos = 1950;

    public int mBoundary = 50;
    private yawPositions mBoundaryPosition;
    public boolean mCheckerPos;
    ElapsedTime timer;

    public enum yawPositions{
        DOWN(yawDownPos),
        CLEAR(yawClearPos),
        SCORE(yawScorePos);

        private final int yawPosition;

        yawPositions(final int newYawPos){
            yawPosition = newYawPos;
        }

        private int getYawPosition(){
            return yawPosition;
        }
    }

    public enum outboardPositions{
        DOWN(outboardRetractedPos),
        FIRST_LINE(outboardFirstLinePos),
        MIDDLE(outboardMiddlePos),
        HIGHEST(outboardHighestPos);

        private final int outboardPosition;

        outboardPositions(final int newOutboardPos){
            outboardPosition = newOutboardPos;
        }

        private int getOutboardPosition(){
            return outboardPosition;
        }

    }

    //defines our RREMX motors.
    public CombineLiftC(HardwareMap hardwareMap) {
        super(hardwareMap);

        outboard = new RREMX(hardwareMap, "liftOutboard", 1.0);

        yaw = new RREMX(hardwareMap, "liftYaw", 1.0);

        timer = new ElapsedTime();
        yaw.setReverse();
        outboard.setReverse();

        setYawTargetPos(getYawPos());
        setOutboardTargetPos(getOutboardPos());
        yaw.runToPositionMode();
        outboard.runToPositionMode();
        //change
        yaw.setPower(0.6);
        outboard.setPower(0.7);


        touchSensor = hardwareMap.get(TouchSensor.class, "boxTouch");

    }

    public void setOutboardCts(int outboardCts) {
        outboard.runToPosition(outboardCts);
        //setting a target for the outboard motor, reliant on encoder counts/ticks
    }

    public void setYawCts(int yawCts) {
        yaw.runToPosition(yawCts);
        // setting a target for the yaw motor, reliant on encoder counts/ticks
    }

    public void resetLiftEncoders(){
        yaw.resetEncoder();
        outboard.resetEncoder();
    }

    public int getOutboardPos() {
        return outboard.getPos();  //retrieves position of outboard motor, relative to encoder tick
    }

    public int getYawPos() {
        return yaw.getPos();  //retrieves position of yaw motor, relative to encoder tick
    }

    public void setYawTargetPos(int yawTarget){
        yaw.setTargetPos(yawTarget);
    }

    public void yawClearanceCkr(){
        double currentPos = getYawPos();
        double underPos = (-mBoundary) + mBoundaryPosition.getYawPosition();
        double overPos = (mBoundary) + mBoundaryPosition.getYawPosition();
        if(!mCheckerPos){
            if(currentPos > underPos && currentPos < overPos){
                mCheckerPos = true;
            }
        }
    }

    public void outboardStateAssigner(String position, yawPositions yawBoundaryPos){
        mBoundaryPosition = yawBoundaryPos;
        if (mCheckerPos) {
            if (position == "Highest") {
                setOutboardHighestPos();
            } else if (position == "Middle") {
                setOutboardMiddlePos();
            } else if (position == "First Line") {
                setOutboardFirstLinePos();
            } else if (position == "Retracted") {
                setOutboardRetracted();
            }
        }
    }

    public void yawStateAssigner(String position){
        if(position == "Score"){
            setYawScore();
        }
        else if(position == "Clearance"){
            setYawClearance();
        }
        else if(position == "Down"){
            setYawDown();
        }
    }

    public void setYawScore(){
        setYawTargetPos(yawPositions.SCORE.getYawPosition());
    }

    public void setYawClearance(){
        setYawTargetPos(yawPositions.CLEAR.getYawPosition());
    }

    public void setYawDown(){
        setYawTargetPos(yawPositions.SCORE.getYawPosition());
    }

    public void setOutboardRetracted(){
        setOutboardTargetPos(outboardPositions.DOWN.getOutboardPosition());
    }

    public void setOutboardFirstLinePos(){
        setOutboardTargetPos(outboardPositions.FIRST_LINE.getOutboardPosition());
    }

    public void setOutboardMiddlePos(){
        setOutboardTargetPos(outboardPositions.MIDDLE.getOutboardPosition());
    }

    public void setOutboardHighestPos(){
        setOutboardTargetPos(outboardPositions.HIGHEST.getOutboardPosition());
    }

    public void setOutboardTargetPos(int outboardTarget){
        outboard.setTargetPos(outboardTarget);
    }

    public void yawCalibrate(){
        if(!calibrated) {
            setYawPower(-0.1);
            outboard.setTargetPos(0);
            if (touchSensor.isPressed()) {
                resetLiftEncoders();
                setYawPower(0);
                calibrated = true;
            }
        }
    }

    public boolean getTouchState(){
        return touchSensor.isPressed();
    }
}
