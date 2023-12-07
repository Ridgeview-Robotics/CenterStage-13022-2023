package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BoxServo {
    Servo boxServo;

    double intake;
    double score;

    public BoxServo(HardwareMap hardwareMap){
        boxServo = hardwareMap.get(Servo.class, "boxServo");
    }

    public void setBoxIntake(){
        boxServo.setPosition(intake);
    }
    public void setBoxScore(){
        boxServo.setPosition(score);
    }
    public void setPosition(double boxPosition){
        boxServo.setPosition(boxPosition);
    }
    public double getPosition(){
        return boxServo.getPosition();
    }
}
