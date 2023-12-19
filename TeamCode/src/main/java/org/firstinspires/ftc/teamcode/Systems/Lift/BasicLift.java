package org.firstinspires.ftc.teamcode.Systems.Lift;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class BasicLift {
    private final RREMX yawMotor;
    private final RREMX outboardMotor;

    public BasicLift(HardwareMap hardwareMap){
        yawMotor = new RREMX(hardwareMap, "liftYaw", 1.0);

        outboardMotor = new RREMX(hardwareMap, "liftOutboard", 1.0); //E3


    }

    public void setYawPower(double yawPower){
        yawMotor.setPower(yawPower);
    }

    public void setOutboardPower(double outboardPower){
        outboardMotor.setPower(outboardPower);
    }
}
