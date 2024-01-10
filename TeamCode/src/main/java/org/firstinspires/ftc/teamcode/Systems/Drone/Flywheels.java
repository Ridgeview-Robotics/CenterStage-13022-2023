package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class Flywheels {

    private RREMX Flywheels;

    public Flywheels(HardwareMap hardwareMap){
        Flywheels = new RREMX(hardwareMap, "flywheels", 1.0); //E3
    }

    public void setFlywheelsPower(double power){
        Flywheels.setPower(power);
    }

    public double getPower(){
        return Flywheels.getPower();
    }

}
