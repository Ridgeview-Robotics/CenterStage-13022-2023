package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
