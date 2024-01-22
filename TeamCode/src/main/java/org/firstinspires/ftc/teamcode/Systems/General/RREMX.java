package org.firstinspires.ftc.teamcode.Systems.General;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RREMX {

    private final double  ticksPerRev = 5281; //GoBILDA 312RPM DC Motor 5204
    public double ticksPerMM; //declaration

    private final DcMotorEx rcsMotor;

    public RREMX(HardwareMap hardwareMap, String motorName, double outputShaftDiameter){

        rcsMotor = hardwareMap.get(DcMotorEx.class, motorName);
        //motor name will change based upon class, declaring a RREMX requires you to pass a "motorName"
        //which will input the string.  ENSURE THAT YOUR motorName MATCHES YOUR HARDWARE CONFIG
        rcsMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);  //when motor is init, it will reset pos
        rcsMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); //post-reset, the motor will run with encoder
        rcsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //when motor power = 0 motor will stop
        rcsMotor.setDirection(DcMotor.Direction.FORWARD);

        ticksPerMM = (ticksPerRev) / (outputShaftDiameter * Math.PI);  //MATH TBD
    }

    public void runToPosition(int targetCts){
        int newTarget = rcsMotor.getCurrentPosition() + (targetCts);//new target = current encoder Pos + target
        rcsMotor.setTargetPosition(newTarget); //runs to new target
        rcsMotor.setPower(targetCts);
    }

    public void loop(){
        if(!rcsMotor.isBusy()){
            rcsMotor.setPower(0);
        }
    }

    public int getPos(){
        return rcsMotor.getCurrentPosition(); //returns the current position as an integer
    }


    public void setPower(double power){
        rcsMotor.setPower(power);
    }

    public void resetEncoder(){
        rcsMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rcsMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setReverse(){
        rcsMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public double getPower(){
        return rcsMotor.getPower();

    }

    /*public void motorLoop(int target){
        if(rcsMotor.getCurrentPosition() != target){
            if((target - rcsMotor.getCurrentPosition()) < 0){
                rcsMotor.setTargetPosition(target);
                rcsMotor.setPower(target - rcsMotor.getCurrentPosition());
            }
            else if(target - rcsMotor.getCurrentPosition() > 0){
                rcsMotor.setTargetPosition(target);
                rcsMotor.setPower(target - rcsMotor.getCurrentPosition());
            }

        }
        else{
            rcsMotor.setPower(0);
        }
    }*/


    public void setTargetPos(int target){
        rcsMotor.setTargetPosition(target);
    }



    public void runUsingEncoderMode(){
        rcsMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToPositionMode(){
        rcsMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}

