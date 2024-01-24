package org.firstinspires.ftc.teamcode.Systems.General;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceFromBoard {
    private HardwareMap hardwareMap;

    private DistanceSensor distFromBoard;

    private double minDist1 = 0;
    private double minDist2 = 30;
    private double minDist3 = 200;
    private double maxDist1 = 35;
    private double maxDist2 = 205;
    private double maxDist3 = 305;

    DistanceFromBoard(HardwareMap hm){
        hardwareMap = hm;

        distFromBoard = hardwareMap.get(DistanceSensor.class, "board_sensor");
    }

    public int getBoardDistRange(){
        int distRange = 4;

        double dist = getDistance();

        if (dist > minDist1 && dist < maxDist1){
            distRange = 1;
        } else if (dist > minDist2 && dist < maxDist2) {
            distRange = 2;
        } else if (dist > minDist3 && dist < maxDist3) {
            distRange = 3;
        }
        return distRange;
    }

    public double getDistance(){
        return distFromBoard.getDistance(DistanceUnit.MM);
    }
}
