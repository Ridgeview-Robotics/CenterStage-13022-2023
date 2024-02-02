package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TrapdoorServo {
    Servo trapdoorServo;

    public static double mTrapdoorOpen = 0.809;
    private static final double mPosError = 0.1;
    public static double mTrapdoorClosed = 0.446;
    public boolean mIsAtDesiredPosition;
    private double mDesiredPosition;

    public enum trapdoorPositions{
        OPEN(mTrapdoorOpen),
        CLOSED(mTrapdoorClosed);

        private final double position;

        trapdoorPositions(final double newPosition){
            position = newPosition;
        }

        private double getPosition(){
            return position;
        }
    }



    public TrapdoorServo(HardwareMap hardwareMap){
        trapdoorServo = hardwareMap.get(Servo.class, "trapdoorServo");
        setTrapdoorOpen();
        mIsAtDesiredPosition = true;
    }

    public void setTrapdoorOpen(){
        LSetPosition(trapdoorPositions.OPEN.position);
    }

    public void setTrapdoorClosed(){
        LSetPosition(trapdoorPositions.CLOSED.position);
    }

    public void GSetPosition(double newPos){
        trapdoorServo.setPosition(newPos);
    }

    public void LSetPosition(double trapdoorPosition){
        trapdoorServo.setPosition(trapdoorPosition);
        mDesiredPosition = trapdoorPosition;
//        mIsAtDesiredPosition = false;
    }
    public double getPosition(){
        return trapdoorServo.getPosition();
    }

    public void update(){
        if(!mIsAtDesiredPosition){
            double currentPos = getPosition();
            double underPos = mDesiredPosition * -mPosError;
            double overPos = mDesiredPosition * mPosError;

            if(currentPos > underPos && currentPos < overPos){
                mIsAtDesiredPosition = true;
            }
        }
    }

    public void togglePosition(){
        mIsAtDesiredPosition = false;
        if(mDesiredPosition == trapdoorPositions.CLOSED.getPosition())
            LSetPosition(trapdoorPositions.OPEN.getPosition());
        else
            LSetPosition(trapdoorPositions.CLOSED.getPosition());
    }
}
