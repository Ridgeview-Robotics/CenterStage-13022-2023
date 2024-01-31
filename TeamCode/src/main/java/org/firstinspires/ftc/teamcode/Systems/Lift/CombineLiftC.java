package org.firstinspires.ftc.teamcode.Systems.Lift;


import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

import java.util.Timer;

public class CombineLiftC extends BasicLift {



    public RREMX outboard;
    public RREMX yaw;

    private TouchSensor touchSensor;

    private boolean calibrated = false;

    public int yawDownPos;
    public final int yawScorePos = 1750;
    public final int outboardFirstLinePos = 525;
    public final int outboardHighestPos = 1950;

    public int outboardRetractedPos;
    ElapsedTime timer;

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
    public void setYawScore(){
        setYawTargetPos(yawScorePos);
    }

    public void setYawDown(){
        setYawTargetPos(yawDownPos);
    }

    public void setOutboardRetracted(){
        setOutboardTargetPos(outboardRetractedPos);
    }

    public void setOutboardFirstLinePos(){
        setOutboardTargetPos(outboardFirstLinePos);
    }
    public void setOutboardHighestPos(){
        setOutboardTargetPos(outboardHighestPos);
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
