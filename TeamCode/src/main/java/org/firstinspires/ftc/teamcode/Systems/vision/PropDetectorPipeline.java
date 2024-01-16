package org.firstinspires.ftc.teamcode.Systems.vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PropDetectorPipeline extends OpenCvPipeline {

    public enum PropPosition{
        LEFT,
        CENTER,
        RIGHT
    }

    Telemetry telemetry;

    static final Scalar BLUE = new Scalar(0, 0, 255);
    static final Scalar SCALAR_RED_COLOR = new Scalar(255, 0, 0);
    double leftAvgFin;
    double centerAvgFin;
    double rightAvgFin;
    Mat YCbCr = new Mat();
    Mat outPut = new Mat();
    Mat leftCropMat;
    Mat centerCropMat;
    Mat rightCropMat;

    Mat RGBMat = new Mat();
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
        Imgproc.rectangle(outPut, leftRect, SCALAR_RED_COLOR, 1);
        Imgproc.rectangle(outPut, centerRect, SCALAR_RED_COLOR, 1);
        Imgproc.rectangle(outPut, rightRect, SCALAR_RED_COLOR, 1);

        leftCropMat = YCbCr.submat(leftRect);  //crash point
        centerCropMat = YCbCr.submat(centerRect);
        rightCropMat = YCbCr.submat(rightRect);

        Core.extractChannel(leftCropMat, leftCropMat, 2);
        Core.extractChannel(centerCropMat, centerCropMat, 2);
        Core.extractChannel(rightCropMat, rightCropMat, 2);

        Scalar leftAvg = Core.mean(leftCropMat);
        Scalar centerAvg = Core.mean(centerCropMat);
        Scalar rightAvg = Core.mean(rightCropMat);

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

    public Mat processFrame2(Mat input)
    {
        int zoneWidth= (639/3);

        Rect leftRect   = new Rect(1, 1, zoneWidth, 320);
        Rect centerRect = new Rect(213,1,zoneWidth, 359);
        Rect rightRect  = new Rect(426,1,zoneWidth, 359);

        input.copyTo(outPut);
        Imgproc.rectangle(outPut, leftRect,     SCALAR_RED_COLOR, 1);
        Imgproc.rectangle(outPut, centerRect,   SCALAR_RED_COLOR, 1);
        Imgproc.rectangle(outPut, rightRect,    SCALAR_RED_COLOR, 1);

        leftCropMat                 = input.submat(leftRect);  //crash point
        centerCropMat               = input.submat(centerRect);
        rightCropMat                = input.submat(rightRect);

        Scalar leftAvg              = Core.mean(leftCropMat);
        Scalar centerAvg            = Core.mean(centerCropMat);
        Scalar rightAvg             = Core.mean(rightCropMat);

        double leftDistanceToRed    = calculateDistance(leftAvg,    SCALAR_RED_COLOR);
        double centerDistanceToRed  = calculateDistance(centerAvg,  SCALAR_RED_COLOR);
        double rightDistanceToRed   = calculateDistance(rightAvg,   SCALAR_RED_COLOR);

        if(leftDistanceToRed < centerDistanceToRed)
        {
            if(leftDistanceToRed < rightDistanceToRed)
                propLocation = "Left";
        }
        else if(centerDistanceToRed < rightDistanceToRed)
            propLocation = "Center";
        else
            propLocation = "Right";

        return outPut;
    }

    //Returns the distance between two scalars
    private double calculateDistance(Scalar one, Scalar two)
    {
        double xDiff = one.val[0] - two.val[0];
        double yDiff = one.val[1] - two.val[1];
        double zDiff = one.val[2] - two.val[2];

        double xSquare = Math.pow(xDiff, 2);
        double ySquare = Math.pow(yDiff, 2);
        double zSquare = Math.pow(zDiff, 2);

        double total = xSquare + ySquare + zSquare;

        double distance = Math.sqrt(total);

        return distance;
    }

    public String getPropLocation(){
        return propLocation;
    }
}
