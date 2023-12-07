package org.firstinspires.ftc.teamcode.Systems.Lift;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class CombineLiftC extends BasicLift {

    //private final RREMX outboard;
    //private final RREMX yaw;

    private RREMX outboard;
    private RREMX yaw;

    private TouchSensor touchSensor;

    private boolean calibrated = false;

    //defines our RREMX motors.
    public CombineLiftC(HardwareMap hardwareMap) {
        super(hardwareMap);

        outboard = new RREMX(hardwareMap, "liftOutboard", 1.0);

        yaw = new RREMX(hardwareMap, "liftYaw", 1.0);
        yaw.setReverse();

        touchSensor = hardwareMap.get(TouchSensor.class, "yawTouch");
    }

    public void setOutboardCts(int outboardCts) {
        outboard.runToCt(outboardCts);  //setting a target for the outboard motor, reliant on encoder counts/ticks
    }

    public void setYawCts(int yawCts) {
        yaw.runToCt(yawCts);  // setting a target for the yaw motor, reliant on encoder counts/ticks
    }

    public int getOutboardPos() {
        return outboard.getPos();  //retrieves position of outboard motor, relative to encoder tick
    }

    public int getYawPos() {
        return yaw.getPos();  //retrieves position of yaw motor, relative to encoder tick
    }

    public void liftLoop(){
        yaw.loop();
        outboard.loop();
    }

    public void yawCalibrate(){
        if(!calibrated) {
            setYawPower(0.25);

            if (touchSensor.isPressed()) {

                setYawPower(0);
                calibrated = true;
                yaw.resetEncoder();
            }
        }
    }

    public boolean getTouchState(){
        return touchSensor.isPressed();
    }
}
