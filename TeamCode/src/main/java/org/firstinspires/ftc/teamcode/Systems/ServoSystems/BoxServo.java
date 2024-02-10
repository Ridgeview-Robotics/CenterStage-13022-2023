package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BoxServo {
    Servo boxServo;

    double intake = 0.3844444;
    double score = 0.8089;
    double storage = 0.39;

    double hangPos = 0.5988;

    public BoxServo(HardwareMap hardwareMap){
        boxServo = hardwareMap.get(Servo.class, "boxServo");
    }



    public void setBoxIntake(){
        boxServo.setPosition(intake);
    }
    public void setBoxScore(){
        boxServo.setPosition(score);
    }

    public void setBoxStorage(){boxServo.setPosition(storage);}

    public void setBoxHang(){
        boxServo.setPosition(hangPos);
    }
    public void setPosition(double boxPosition){
        boxServo.setPosition(boxPosition);
    }
    public double getPosition(){
        return boxServo.getPosition();
    }
}
