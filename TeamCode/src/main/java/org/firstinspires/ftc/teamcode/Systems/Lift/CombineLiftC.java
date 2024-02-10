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

    public static final int yawDownPos = 0;
    public static final int outboardRetractedPos = 0;
    public static final int yawClearPos = 675;
    public static final int yawScorePos = 1855;
    public static final int outboardFirstLinePos = 525;
    public static final int outboardAutoPos = 345;
    public static final int outboardMiddlePos = 1000;
    public static final int outboardHighestPos = 1950;
    public static final int outboardHangingPos = 100;


    public int mLBoundary = 100;
    public int mHBoundary = 800;
    public yawPositions mBoundaryPosition;
    public boolean mCheckerPos;
    public outboardPositions mNOutPosition;
    ElapsedTime timer;

    public yawPositions mYawPosition;
    public outboardPositions mOutboardPosition;

    public enum yawPositions{
        DOWN(yawDownPos),
        CLEAR(yawClearPos),
        SCORE(yawScorePos);

        private final int yawPosition;

        yawPositions(final int newYawPos){
            yawPosition = newYawPos;
        }

        public int getYawPosition(){
            return yawPosition;
        }
    }

    public enum outboardPositions{
        DOWN(outboardRetractedPos),
        AUTO_POS(outboardAutoPos),
        HANG_TARGET_POS(outboardHangingPos),
        FIRST_LINE(outboardFirstLinePos),
        MIDDLE(outboardMiddlePos),
        HIGHEST(outboardHighestPos);

        private final int outboardPosition;

        outboardPositions(int newOutboardPos){
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
        mOutboardPosition = outboardPositions.DOWN;
        mYawPosition = yawPositions.DOWN;
        setYawTargetPos(getYawState());
        setOutboardTargetPos(getOutboardState());
        yaw.runToPositionMode();
        outboard.runToPositionMode();
        //change
        yaw.setPower(0.5);
        outboard.setPower(1.0);


        touchSensor = hardwareMap.get(TouchSensor.class, "boxTouch");

        mBoundaryPosition = null;
    }

    public void setOutboardCts(int outboardCts) {
        outboard.runToPosition(outboardCts);
        //setting a target for the outboard motor, reliant on encoder counts/ticks
    }

    public void setYawCts(int yawCts) {
        yaw.runToPosition(yawCts);
        // setting a target for the yaw motor, reliant on encoder counts/ticks
    }

    public yawPositions getYawState(){
        return mYawPosition;
    }

    public outboardPositions getOutboardState(){
        return mOutboardPosition;
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

    public void setYawTargetPos(yawPositions yawTarget){
        yaw.setTargetPos(yawTarget.getYawPosition());
    }

    public void yawClearanceCkr(){
        mCheckerPos = false;
        if(mBoundaryPosition != null) {
            double currentPos = getYawPos();
            double underPos = (-mLBoundary) + mBoundaryPosition.getYawPosition();
            double overPos = (mHBoundary) + mBoundaryPosition.getYawPosition();
            if (!mCheckerPos) {
                if (currentPos > underPos && currentPos < overPos) {
                    mCheckerPos = true;
                }
            }
        }
    }

    public void outboardStateAssigner(outboardPositions position, yawPositions yawBoundaryPos){
        mBoundaryPosition = yawBoundaryPos;
        if (mCheckerPos) {
            setOutboardTargetPos(position);
        }
    }

    public void yawStateAssigner(yawPositions position){
        setYawTargetPos(position);
        mYawPosition = position;
    }

    public void setYawScore(){
        setYawTargetPos(yawPositions.SCORE);
    }

    public void setYawClearance(){
        setYawTargetPos(yawPositions.CLEAR);
    }

    public void setYawDown(){
        setYawTargetPos(yawPositions.DOWN);
    }

    public void setOutboardRetracted(){
        setOutboardTargetPos(outboardPositions.DOWN);
    }

    public void setOutboardAutoPos(){
        setOutboardTargetPos(outboardPositions.AUTO_POS);
    }

    public void setOutboardFirstLinePos(){
        setOutboardTargetPos(outboardPositions.FIRST_LINE);
    }

    public void setOutboardMiddlePos(){
        setOutboardTargetPos(outboardPositions.MIDDLE);
    }

    public void setOutboardHighestPos(){
        setOutboardTargetPos(outboardPositions.HIGHEST);
    }

    public void setOutboardTargetPos(outboardPositions outboardTarget){
        outboard.setTargetPos(outboardTarget.getOutboardPosition());
        mOutboardPosition = outboardTarget;
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
