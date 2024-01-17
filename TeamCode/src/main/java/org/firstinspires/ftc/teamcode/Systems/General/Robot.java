package org.firstinspires.ftc.teamcode.Systems.General;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Systems.Drone.Flywheels;
import org.firstinspires.ftc.teamcode.Systems.Intake.Intake;
import org.firstinspires.ftc.teamcode.Systems.Lift.CombineLiftC;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.BoxServo;
import org.firstinspires.ftc.teamcode.Systems.ServoSystems.TrapdoorServo;
import org.firstinspires.ftc.teamcode.Systems.vision.SignalDetector;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Robot {
    public SampleMecanumDrive autoDrive;

    CombineLiftC lift;
    public Drive drive;
    BoxServo boxServo;
    TrapdoorServo trapdoorServo;
    Intake intake;
    Flywheels drone;
    SignalDetector signalDetector;


    public Robot(Telemetry telemetry, HardwareMap hardwareMap, boolean isAuto){
        autoDrive = new SampleMecanumDrive(hardwareMap);
        lift = new CombineLiftC(hardwareMap);
        drive = new Drive(hardwareMap);
        boxServo = new BoxServo(hardwareMap);
        trapdoorServo = new TrapdoorServo(hardwareMap);
        intake = new Intake(hardwareMap);
        drone = new Flywheels(hardwareMap);

        if(isAuto){
            signalDetector = new SignalDetector(hardwareMap, telemetry);
        }

    }

    public void setDrivePower(double power1, double power2, double power3, double power4){
        drive.setMotorPower(power1, power2, power3, power4);
    }

    public void setYawTarget(int pos){
        lift.setYawTargetPos(pos);
    }

    public void setOutboardTarget(int pos){
        lift.setOutboardTargetPos(pos);
    }

    public void setYawPower(double power){
        lift.setYawPower(power);
    }

    public void setOutboardPower(double power){
        lift.setOutboardPower(power);
    }

    public void setIntakeSpeed(double speed){
        intake.setIntakePower(speed);
    }

    public void setBoxScore(){
        boxServo.setBoxScore();
    }

    public void setBoxIntake(){
        boxServo.setBoxIntake();
    }
    public void setBoxStorage(){boxServo.setBoxStorage();}

    public void setTrapdoorOpen(){
        trapdoorServo.setTrapdoorOpen();
    }

    public void setTrapdoorClosed(){
        trapdoorServo.setTrapdoorClosed();
    }

    public void setFlywheelSpeed(double power){
        drone.setFlywheelsPower(power);
    }

    public int yawDownPos(){
        return lift.yawDownPos;
    }

    public int outboardRetractedPos(){
        return lift.outboardRetractedPos;
    }

}
