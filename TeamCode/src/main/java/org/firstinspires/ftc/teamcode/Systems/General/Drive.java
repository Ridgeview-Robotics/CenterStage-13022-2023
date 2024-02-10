package org.firstinspires.ftc.teamcode.Systems.General;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drive {
    private final DcMotorEx leftFrontDrive;
    private final DcMotorEx leftBackDrive;
    private final DcMotorEx rightFrontDrive;
    private final DcMotorEx rightBackDrive;

    private final double mTicksPerRev = 383.6;

    private final double mWheelRadius = 1.8898;

    public Drive(HardwareMap hardwareMap){
        leftFrontDrive  = hardwareMap.get(DcMotorEx.class, "left_front_drive");
        leftBackDrive  = hardwareMap.get(DcMotorEx.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "right_back_drive");


        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);


        setMotorPower(0, 0, 0, 0);

    }

    public void setMotorPower(double leftFront, double rightFront, double leftBack, double rightBack){
        leftFrontDrive.setPower(leftFront);
        rightFrontDrive.setPower(rightFront);
        leftBackDrive.setPower(leftBack);
        rightBackDrive.setPower(rightBack);
    }

    public void setMode(DcMotor.RunMode runMode){
        leftFrontDrive.setMode(runMode);
        rightFrontDrive.setMode(runMode);
        leftBackDrive.setMode(runMode);
        rightBackDrive.setMode(runMode);
    }

    public void setDriveTarget(double inches){
        int ticks = (int) inchesToTicks(inches);
        leftFrontDrive.setTargetPosition(ticks);
        rightFrontDrive.setTargetPosition(ticks);
        leftBackDrive.setTargetPosition(ticks);
        rightBackDrive.setTargetPosition(ticks);
    }

    private double inchesToTicks(double inches){
        return ((inches*mTicksPerRev)/mWheelRadius * 2 * Math.PI);
    }

    public boolean isBusy(){
        return leftFrontDrive.isBusy();
    }

    public void stopAndReset(){
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
