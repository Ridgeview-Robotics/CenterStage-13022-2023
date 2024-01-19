package org.firstinspires.ftc.teamcode.Systems.Lift;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class CombineLiftC extends BasicLift {



    public RREMX outboard;
    public RREMX yaw;

    private TouchSensor touchSensor;

    private boolean calibrated = false;

    public int yawDownPos;

    public int outboardRetractedPos;

    //defines our RREMX motors.
    public CombineLiftC(HardwareMap hardwareMap) {
        super(hardwareMap);

        outboard = new RREMX(hardwareMap, "liftOutboard", 1.0);

        yaw = new RREMX(hardwareMap, "liftYaw", 1.0);
        yaw.setReverse();

        //yaw.runToPositionMode();
        //setYawTargetPos(getYawPos());
        //setOutboardTargetPos(getOutboardPos());

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

    public void setOutboardTargetPos(int outboardTarget){
        outboard.setTargetPos(outboardTarget);
    }



    public void yawCalibrate(){
        if(!calibrated) {
            setYawPower(-0.1);

            if (touchSensor.isPressed()) {

                setYawPower(0);
                calibrated = true;
                yaw.resetEncoder();
                yawDownPos = getYawPos();
                outboardRetractedPos = getOutboardPos();
            }
        }
    }

    public boolean getTouchState(){
        return touchSensor.isPressed();
    }
}
