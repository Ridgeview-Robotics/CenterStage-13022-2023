package org.firstinspires.ftc.teamcode.Systems;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class CombineLiftC extends BasicLift {

    //private final RREMX outboard;
    //private final RREMX yaw;

    private RREMX outboard;
    private RREMX yaw;

    private TouchSensor touchSensor;

    //defines our RREMX motors.
    public CombineLiftC(HardwareMap hardwareMap) {
        super(hardwareMap);
        outboard = new RREMX(hardwareMap, "liftOutboard", 1.0);
        //outboard = hardwareMap.get(RREMX.class, "liftOutboard");
        yaw = new RREMX(hardwareMap, "liftYaw", 1.0);
        yaw.setReverse();
        //yaw = hardwareMap.get(RREMX.class, "liftYaw");
        //local name = RREMX(hardware map, name of motor, final output element diameter)
        touchSensor = hardwareMap.get(TouchSensor.class, "yawTouch");
        yawTouch();
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

    public void yawTouch(){
        setYawPower(0.25);

        if(touchSensor.isPressed()){
            setYawPower(0);
        }
        yaw.resetEncoder();
    }
}
