package org.firstinspires.ftc.teamcode.Systems.General.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class BlueLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //----LEFT MOVEMENTS----//
            //To Pixel
        //TrajectorySequence leftToPixel = drive.trajectorySequenceBuilder(new Pose2d(11.94, 63.25, Math.toRadians(270.00)))
        //.splineTo(new Vector2d(21.13, 41.30), Math.toRadians(-72.76))
        //.build();
        //drive.setPoseEstimate(leftToPixel.start());
            //To Backstage
        //TrajectorySequence leftToBackStage = drive.trajectorySequenceBuilder(new Pose2d(21.13, 41.30, Math.toRadians(-72.76)))
        //.splineTo(new Vector2d(22.02, 55.98), Math.toRadians(-68.92))
        //.splineTo(new Vector2d(59.54, 58.95), Math.toRadians(6.51))
        //.build();
        //drive.setPoseEstimate(leftToBackStage.start());

        //----CENTER MOVEMENTS----//
            //To Pixel
        //TrajectorySequence centerToPixel = drive.trajectorySequenceBuilder(new Pose2d(11.49, 63.10, Math.toRadians(270.00)))
        //.splineTo(new Vector2d(11.20, 32.85), Math.toRadians(257.91))
        //.build();
        //drive.setPoseEstimate(centerToPixel.start());
            //To Backstage
        //TrajectorySequence centerToBackStage = drive.trajectorySequenceBuilder(new Pose2d(11.20, 32.85, Math.toRadians(257.91)))
        //.splineTo(new Vector2d(19.95, 54.06), Math.toRadians(239.22))
        //.splineTo(new Vector2d(59.69, 60.58), Math.toRadians(5.53))
        //.build();
        //drive.setPoseEstimate(centerToBackStage.start());

        //----RIGHT MOVEMENTS----//
            //To Pixel
        //TrajectorySequence centerToPixel = drive.trajectorySequenceBuilder(new Pose2d(10.60, 63.10, Math.toRadians(270.00)))
        //.splineTo(new Vector2d(8.08, 40.41), Math.toRadians(220.16))
        //.build();
        //drive.setPoseEstimate(centerToPixel.start());
            //To BackStage
        //TrajectorySequence centerToBackStage = drive.trajectorySequenceBuilder(new Pose2d(7.49, 39.82, Math.toRadians(230.08)))
        //.splineTo(new Vector2d(25.73, 54.80), Math.toRadians(223.13))
        //.splineTo(new Vector2d(58.50, 59.39), Math.toRadians(8.13))
        //.build();
        //drive.setPoseEstimate(centerToBackStage.start());
    }


}
