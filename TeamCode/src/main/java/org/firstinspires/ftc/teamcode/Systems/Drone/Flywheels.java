package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class Flywheels {

    private RREMX Flywheels;
    DroneHolderServo servo;


    public Flywheels(HardwareMap hardwareMap){
        Flywheels = new RREMX(hardwareMap, "flywheels", 1.0); //E3
        servo = new DroneHolderServo(hardwareMap);
    }

    public void setFlywheelsPower(double power){
        Flywheels.setPower(power);
    }

    public double getPower(){
        return Flywheels.getPower();
    }

    public void primeDrone(){
        servo.setDroneServoTaut();
    }

    public void shootDrone(){
        servo.setDroneServoRelease();
    }

}
