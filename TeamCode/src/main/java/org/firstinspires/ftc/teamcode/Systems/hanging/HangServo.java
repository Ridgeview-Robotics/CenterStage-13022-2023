package org.firstinspires.ftc.teamcode.Systems.hanging;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HangServo {
    Servo servo;

    public double downPosition;

    public double raisedPosition;

    public double pullOffPosition;

    public HangServo(HardwareMap hardwareMap){
        servo = hardwareMap.get(Servo.class, "liftingServo");
    }

    public void setPosition(double position){
        servo.setPosition(position);
    }

    public double getPosition(){
        return servo.getPosition();
    }

    public void setDownPosition(){
        servo.setPosition(downPosition);
    }

    public void setRaisedPosition(){
        servo.setPosition(raisedPosition);
    }

    public void setPullOffPosition(){
        servo.setPosition(pullOffPosition);
    }

}
