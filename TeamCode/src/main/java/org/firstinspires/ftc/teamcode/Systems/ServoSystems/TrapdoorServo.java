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
        setPosition(trapdoorPositions.OPEN.position);
    }

    public void setTrapdoorClosed(){
        setPosition(trapdoorPositions.CLOSED.position);
    }

    public void setPosition(double trapdoorPosition){
        trapdoorServo.setPosition(trapdoorPosition);
        mDesiredPosition = trapdoorPosition;
        mIsAtDesiredPosition = false;
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
        if(mDesiredPosition == trapdoorPositions.CLOSED.getPosition())
            setPosition(trapdoorPositions.OPEN.getPosition());
        else
            setPosition(trapdoorPositions.CLOSED.getPosition());
    }
}
