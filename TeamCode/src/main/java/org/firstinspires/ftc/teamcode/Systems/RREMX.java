package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RREMX {

    private final double  ticksPerRev = 28; //GoBILDA 312RPM DC Motor 5204
    public double ticksPerMM; //declaration

    private final DcMotorEx rcsMotor;

    public RREMX(HardwareMap hardwareMap, String motorName, double outputShaftDiameter){

        rcsMotor = hardwareMap.get(DcMotorEx.class, motorName);
        //motor name will change based upon class, declaring a RREMX requires you to pass a "motorName"
        //which will input the string.  ENSURE THAT YOUR motorName MATCHES YOUR HARDWARE CONFIG
        rcsMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);  //when motor is init, it will reset pos
        rcsMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER); //post-reset, the motor will run with encoder
        rcsMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //when motor power = 0 motor will stop

        ticksPerMM = (ticksPerRev) / (outputShaftDiameter * Math.PI);  //MATH TBD
    }

    public void runToCt(int targetCts){
        int newTarget = rcsMotor.getCurrentPosition() + (targetCts);  //new target = current encoder Pos + target
        rcsMotor.setTargetPosition(newTarget); //runs to new target
    }

    public int getPos(){
        return rcsMotor.getCurrentPosition(); //returns the current position as an integer
    }

}