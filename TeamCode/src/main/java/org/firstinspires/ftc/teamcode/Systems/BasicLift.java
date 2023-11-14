package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BasicLift {
    private final DcMotorEx yawMotor;
    private final DcMotorEx outboardMotor;

    public BasicLift(HardwareMap hardwareMap){
        yawMotor = hardwareMap.get(DcMotorEx.class, "liftYaw"); //E2
        yawMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outboardMotor = hardwareMap.get(DcMotorEx.class, "liftOutboard"); //E3
        outboardMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void setYawPower(double yawPower){
        yawMotor.setPower(yawPower);
    }

    public void setOutboardPower(double outboardPower){
        outboardMotor.setPower(outboardPower);
    }
}
