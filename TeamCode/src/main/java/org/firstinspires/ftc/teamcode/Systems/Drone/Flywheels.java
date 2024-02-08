package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class Flywheels {


    DroneHolderServo servo;


    public Flywheels(HardwareMap hardwareMap){
        servo = new DroneHolderServo(hardwareMap);
    }

    public void primeDrone(){
        servo.setDroneServoTaut();
    }

    public void shootDrone(){
        servo.setDroneServoRelease();
    }

}
