package org.firstinspires.ftc.teamcode.Systems.hanging;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Systems.ServoSystems.TrapdoorServo;

public class HangServo {
    Servo servo;
    ElapsedTime timer;

    public static double downPosition;
    public hangPositions mDesiredPosition;

    public static double raisedPosition;

    public enum hangPositions{
        DOWN(downPosition),
        RAISED(raisedPosition);

        private final double position;

        hangPositions(final double newPosition){
            position = newPosition;
        }

        private double getPosition(){
            return position;
        }
    }

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

    public void LSetPosition(hangPositions hangPositions){
        timer.reset();
        mDesiredPosition = hangPositions;
        servo.setPosition(hangPositions.getPosition());


    }

    public void togglePosition(){
        if(timer.milliseconds() > 500){
            if(mDesiredPosition == hangPositions.DOWN) {
                LSetPosition(hangPositions.RAISED);
            }
            else {
                LSetPosition(hangPositions.DOWN);
            }
        }
//        mIsAtDesiredPosition = false;

    }


}
