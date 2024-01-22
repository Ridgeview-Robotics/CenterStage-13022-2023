package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TrapdoorServo {
    Servo trapdoorServo;

    public double trapdoorOpen= 0.326;

    public double trapdoorClosed = 0.65;

    public TrapdoorServo(HardwareMap hardwareMap){
        trapdoorServo = hardwareMap.get(Servo.class, "trapdoorServo");
    }

    public void setTrapdoorOpen(){
        trapdoorServo.setPosition(trapdoorOpen);
    }

    public void setTrapdoorClosed(){
        trapdoorServo.setPosition(trapdoorClosed);
    }

    public void setPosition(double trapdoorPosition){
        trapdoorServo.setPosition(trapdoorPosition);
    }
    public double getPosition(){
        return trapdoorServo.getPosition();
    }
}
