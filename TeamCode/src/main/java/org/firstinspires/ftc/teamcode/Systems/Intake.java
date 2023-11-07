package org.firstinspires.ftc.teamcode.Systems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

private final DcMotorEx leftFlipper;
private final DcMotorEx rightFlipper;

    public Intake(HardwareMap hardwareMap){
        leftFlipper = hardwareMap.get(DcMotorEx.class, "leftFlipper");  //E0
        rightFlipper = hardwareMap.get(DcMotorEx.class, "rightFlipper");  //E1
        leftFlipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFlipper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setIntakePower(0);
    }

    public void setIntakePower(double power){
        leftFlipper.setPower(-power);
        rightFlipper.setPower(power);
    }

}
