package org.firstinspires.ftc.teamcode.Systems.Drone;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.security.Provider;

public class DroneHolderServo {

    Servo droneHolder;

    double taut;
    double release;

    public DroneHolderServo(HardwareMap hardwareMap){
        droneHolder = hardwareMap.get(Servo.class, "droneServo");
    }

    public void setDroneServoTaut(){
        droneHolder.setPosition(taut);
    }

    public void setDroneServoRelease(){
        droneHolder.setPosition(release);
    }

    public double getDroneServoPos(){
        return droneHolder.getPosition();
    }

    public void setDroneHolderPosition(double droneHolderPosition){
        droneHolder.setPosition(droneHolderPosition);
    }

}
