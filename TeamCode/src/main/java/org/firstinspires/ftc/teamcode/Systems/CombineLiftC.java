package org.firstinspires.ftc.teamcode.Systems;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CombineLiftC extends BasicLift {

    //private final RREMX outboard;
    //private final RREMX yaw;

    private RREMX outboard;
    private RREMX yaw;

    //defines our RREMX motors.
    public CombineLiftC(HardwareMap hardwareMap) {
        super(hardwareMap);
        outboard = new RREMX(hardwareMap, "liftOutboard", 1.0);
        outboard = hardwareMap.get(RREMX.class, "liftOutboard");
        yaw = new RREMX(hardwareMap, "liftYaw", 1.0);
        yaw = hardwareMap.get(RREMX.class, "liftYaw");

        //local name = RREMX(hardware map, name of motor, final output element diameter)
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
}
