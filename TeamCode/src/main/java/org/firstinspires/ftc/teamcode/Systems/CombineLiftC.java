package org.firstinspires.ftc.teamcode.Systems;


import com.qualcomm.robotcore.hardware.HardwareMap;

public class CombineLiftC extends LiftEncoder{

    private final RREMX outboard;
    private final RREMX yaw;

        //defines our RREMX motors.
    public CombineLiftC(HardwareMap hardwareMap) {
        super(hardwareMap); //due to extension of LiftEncoder class
        outboard = new RREMX(hardwareMap, "liftOutboard", 1.0);
        yaw = new RREMX(hardwareMap, "liftYaw", 1.0);
        //local name = RREMX(hardware map, name of motor, final output element diameter)
    }

    public void setOutboardCts(int outboardCts){
        outboard.runToCt(outboardCts);  //setting a target for the outboard motor, reliant on encoder counts/ticks
    }
    public void setYawCts(int yawCts){
        yaw.runToCt(yawCts);  // setting a target for the yaw motor, reliant on encoder counts/ticks
    }

    public int getOutboardPos(){
       return outboard.getPos();  //retrieves position of outboard motor, relative to encoder tick
    }
    public int getYawPos(){
        return yaw.getPos();  //retrieves position of yaw motor, relative to encoder tick
    }
}
