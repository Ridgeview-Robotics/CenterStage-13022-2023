package org.firstinspires.ftc.teamcode.Systems.hanging;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class HangMotor {
    RREMX hangMotor;

    public HangMotor(HardwareMap hardwareMap){
        hangMotor = new RREMX(hardwareMap, "hangMotor", 1.0);
    }

    public void setHangMotorPower(double power){
        hangMotor.setPower(power);
    }

    public void runToPosMode(){
        hangMotor.runToPositionMode();
    }

    public void runWithEncoderMode(){
        hangMotor.runUsingEncoderMode();
    }

}
