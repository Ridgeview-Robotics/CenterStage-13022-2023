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

    static final Scalar SCALAR_BLUE_COLOR = new Scalar(0, 0, 255);
    static final Scalar SCALAR_RED_COLOR = new Scalar(255, 0, 0);
    public Scalar leftAvg;
    public Scalar centerAvg;
    public Scalar rightAvg;
    double leftAvgFin;
    double centerAvgFin;
    double rightAvgFin;
    double leftDistanceToRed;
    double centerDistanceToRed;
    double rightDistanceToRed;
    Mat YCbCr = new Mat();
    Mat outPut = new Mat();
    Mat leftCropMat;
    Mat centerCropMat;
    Mat rightCropMat;

    Mat RGBMat = new Mat();
    String propLocation = null;
    public boolean mRedBol;

    public Scalar mScalCheck(){
         if(mRedBol){
             return SCALAR_RED_COLOR;
         }
         else if(!mRedBol){
             return SCALAR_BLUE_COLOR;
         }
         else return null;
    }

    public PropDetectorPipeline(Telemetry t, boolean colorRed){
        telemetry = t;
        mRedBol = colorRed;
    }

    @Override
    public Mat processFrame(Mat input)
    {
        int zoneWidth= 175;

        Rect leftRect   = new Rect(1, 185, 120, 175);
        Rect centerRect = new Rect(310,180,75, 100);
        Rect rightRect  = new Rect(519,185,120, 175);

        input.copyTo(outPut);
        Imgproc.rectangle(outPut, leftRect,     SCALAR_RED_COLOR, 1);
        Imgproc.rectangle(outPut, centerRect,   SCALAR_RED_COLOR, 1);
        Imgproc.rectangle(outPut, rightRect,    SCALAR_RED_COLOR, 1);

        leftCropMat                 = input.submat(leftRect);  //crash point
        centerCropMat               = input.submat(centerRect);
        rightCropMat                = input.submat(rightRect);

        leftAvg              = Core.mean(leftCropMat);
        centerAvg            = Core.mean(centerCropMat);
        rightAvg             = Core.mean(rightCropMat);

        leftDistanceToRed    = calculateDistance(leftAvg,    mScalCheck());
        centerDistanceToRed  = calculateDistance(centerAvg,  mScalCheck());
        rightDistanceToRed   = calculateDistance(rightAvg,   mScalCheck());

        if(leftDistanceToRed < centerDistanceToRed && leftDistanceToRed < rightDistanceToRed)
        {
            propLocation = "Left";
        }
        else if(centerDistanceToRed < rightDistanceToRed && centerDistanceToRed < leftDistanceToRed)
            propLocation = "Center";
        else if(rightDistanceToRed < leftDistanceToRed && rightDistanceToRed < centerDistanceToRed) {
            propLocation = "Right";
        }

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

    public String getPropLocation(boolean color){
        //mRedBol = color;
        return propLocation;
    }




}