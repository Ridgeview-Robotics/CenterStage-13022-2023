package org.firstinspires.ftc.teamcode.Systems.Intake;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

private final DcMotorEx intake;

private PixNumSensor pixNumSensor;

    public Intake(HardwareMap hardwareMap){
        pixNumSensor = new PixNumSensor(hardwareMap);

        intake = hardwareMap.get(DcMotorEx.class, "intake");  //E0
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setIntakePower(0);
    }

    public void setIntakePower(double power){
        intake.setPower(power);
    }

    public int getNumPix(){
        return pixNumSensor.getNumPix();
    }

}
