package org.firstinspires.ftc.teamcode.Systems.Lift;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Systems.General.RREMX;

public class Hanging {
    private final RREMX hangingMotor;
    private static final int hangVal = 100;

    Hanging(HardwareMap hardwareMap){
        hangingMotor = new RREMX(hardwareMap, "hangingMotor", 1.0);

        hangingMotor.runToPositionMode();
        hangingMotor.setPower(1.0);
    }

    public void hang(){
        hangingMotor.setTargetPos(hangVal);
    }

}
