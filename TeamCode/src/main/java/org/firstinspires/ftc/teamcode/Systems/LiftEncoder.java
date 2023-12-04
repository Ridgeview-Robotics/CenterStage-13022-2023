/* package org.firstinspires.ftc.teamcode.Systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftEncoder extends BasicLift{

    static final double  TICKS_PER_REV = 28;
    static final double SPOOL_DIAMETER_MM = 15;
    static final double TICKS_PER_MM = (TICKS_PER_REV) / (SPOOL_DIAMETER_MM * Math.PI);
    int newLiftTarget;


    private DcMotorEx liftDrive;

    public LiftEncoder(HardwareMap hardwareMap){
        super(hardwareMap);
        liftDrive = hardwareMap.get(DcMotorEx.class, "liftMotor");

        liftDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        liftDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void runToPos(int targetCts){
        newLiftTarget = liftDrive.getCurrentPosition() + (targetCts);
        liftDrive.setTargetPosition(newLiftTarget);
    }

    public int getPos(){
        return liftDrive.getCurrentPosition();
    }

}
*/