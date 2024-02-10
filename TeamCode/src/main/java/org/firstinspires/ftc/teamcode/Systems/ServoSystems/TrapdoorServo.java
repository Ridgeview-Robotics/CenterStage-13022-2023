package org.firstinspires.ftc.teamcode.Systems.ServoSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Systems.General.Robot;

public class TrapdoorServo {
    Servo trapdoorServo;

    Telemetry mTelemetry;

    ElapsedTime timer;


    public static double mTrapdoorOpen = 0.809;
    private static final double mPosError = 0.01;
    public static double mTrapdoorClosed = 0.446;
    private trapdoorPositions mDesiredPosition;


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



    public TrapdoorServo(HardwareMap hardwareMap, Telemetry telemetry){
        trapdoorServo = hardwareMap.get(Servo.class, "trapdoorServo");
        mTelemetry = telemetry;
        timer = new ElapsedTime();
        LSetPosition(trapdoorPositions.OPEN);
    }

    public void setTrapdoorOpen(){
        LSetPosition(trapdoorPositions.OPEN);
    }

    public void setTrapdoorClosed(){
        LSetPosition(trapdoorPositions.CLOSED);
    }

    public void GSetPosition(double newPos){
        trapdoorServo.setPosition(newPos);
    }

    public void LSetPosition(trapdoorPositions trapdoorPosition){
        timer.reset();
        mDesiredPosition = trapdoorPosition;
        trapdoorServo.setPosition(trapdoorPosition.getPosition());
    }
    public double getPosition(){
        return trapdoorServo.getPosition();
    }

    public void update(){
//        double currentPos = getPosition();
//        double underPos = (-mPosError) + mDesiredPosition.getPosition();
//        double overPos = (mPosError) + mDesiredPosition.getPosition();
//        if(!mIsAtDesiredPosition){
//            if(currentPos > underPos && currentPos < overPos){
//                mIsAtDesiredPosition = true;
//            }
//
//        }
//        mTelemetry.addLine("Current Pos: " + currentPos);
//        mTelemetry.addLine("Under: " + underPos);
//        mTelemetry.addLine("Over: " + overPos);
//        mTelemetry.addLine("State: " + mDesiredPosition);
//        mTelemetry.addLine("Time: " + timer.milliseconds());
    }

    public void togglePosition(){
        if(timer.milliseconds() > 500){
            if(mDesiredPosition == trapdoorPositions.CLOSED) {
                Robot.mTrapdoorPos = trapdoorPositions.OPEN;
                LSetPosition(trapdoorPositions.OPEN);
            }
            else {
                Robot.mTrapdoorPos = trapdoorPositions.CLOSED;
                LSetPosition(trapdoorPositions.CLOSED);
            }
        }
//        mIsAtDesiredPosition = false;

    }
}
