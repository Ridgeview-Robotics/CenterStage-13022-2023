package org.firstinspires.ftc.teamcode.Systems.vision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectorPipeline extends OpenCvPipeline {

    public enum PropPosition{
        LEFT,
        CENTER,
        RIGHT
    }

    Telemetry telemetry;

    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar RED = new Scalar(255, 0, 0);
    double leftAvgFin;
    double centerAvgFin;
    double rightAvgFin;
    Mat YCbCr = new Mat();
    Mat outPut = new Mat();
    Mat leftCrop;
    Mat centerCrop;
    Mat rightCrop;

    String propLocation = null;

    public PropDetectorPipeline(Telemetry t){
        telemetry = t;
    }

    @Override
    public Mat processFrame(Mat input){

        Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);

        int zoneWidth= (639/3);

        Rect leftRect   = new Rect(1, 1, zoneWidth, 320);
        Rect centerRect = new Rect(213,1,zoneWidth, 359);
        Rect rightRect  = new Rect(426,1,zoneWidth, 359);

        input.copyTo(outPut);
        Imgproc.rectangle(outPut, leftRect,RED, 1);
        Imgproc.rectangle(outPut, centerRect,RED, 1);
        Imgproc.rectangle(outPut, rightRect,RED, 1);

        leftCrop = YCbCr.submat(leftRect);  //crash point
        centerCrop = YCbCr.submat(centerRect);
        rightCrop = YCbCr.submat(rightRect);

        Core.extractChannel(leftCrop, leftCrop, 2);
        Core.extractChannel(centerCrop, centerCrop, 2);
        Core.extractChannel(rightCrop, rightCrop, 2);

        Scalar leftAvg = Core.mean(leftCrop);
        Scalar centerAvg = Core.mean(centerCrop);
        Scalar rightAvg = Core.mean(rightCrop);

        leftAvgFin = leftAvg.val[0];
        centerAvgFin = centerAvg.val[0];
        rightAvgFin = rightAvg.val[0];

        if(leftAvgFin > centerAvgFin){
            if(leftAvgFin > rightAvgFin){
                propLocation = "Left";
            }

        }
        else if(centerAvgFin > rightAvgFin){
            propLocation = "Center";
        }
        else{
            propLocation = "Right";
        }

        return outPut;
    }

    public String getPropLocation(){
        return propLocation;
    }
}
