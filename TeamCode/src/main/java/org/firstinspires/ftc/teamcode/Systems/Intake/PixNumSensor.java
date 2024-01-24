package org.firstinspires.ftc.teamcode.Systems.Intake;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PixNumSensor {
    private DistanceSensor pixSense;

    private double minDoublePixDist = 25;
    private double maxDoublePixDist = 50;
    private double minSinglePixDist = 75;
    private double maxSinglePixDist = 100;

    public PixNumSensor(HardwareMap hardwareMap){
        pixSense = hardwareMap.get(DistanceSensor.class, "pixel_sensor");
    }

    public int getNumPix(){
        double dist = pixSense.getDistance(DistanceUnit.MM);

        int numPix = 0;

        if (dist > minSinglePixDist && dist < maxSinglePixDist){
            numPix = 1;
        } else if (dist > minDoublePixDist && dist < maxDoublePixDist) {
            numPix = 2;
        }

        return numPix;
    }
}
