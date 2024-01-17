package org.firstinspires.ftc.teamcode.Systems.General.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class RedLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //----LEFT MOVEMENTS----//
            //To Pixel Drop
        //TrajectorySequence leftToPixel = drive.trajectorySequenceBuilder(new Pose2d(-35.37, -62.81, Math.toRadians(90.00)))
        //.splineTo(new Vector2d(-45.31, -39.82), Math.toRadians(108.19))
        //.build();
        //drive.setPoseEstimate(leftToPixel.start());
            //Away from pixel and to backstage
        //TrajectorySequence leftToBackStage = drive.trajectorySequenceBuilder(new Pose2d(-45.31, -39.82, Math.toRadians(108.19)))
        //.splineTo(new Vector2d(-41.60, -58.95), Math.toRadians(101.13))
        //.splineTo(new Vector2d(-26.47, -4.82), Math.toRadians(73.66))
        //.splineTo(new Vector2d(59.25, -11.94), Math.toRadians(-6.31))
        //.build();
        //drive.setPoseEstimate(leftToBackStage.start());
        //

        //----CENTER MOVEMENTS----//
            //To Pixel Drop
        //TrajectorySequence centerToPixel = drive.trajectorySequenceBuilder(new Pose2d(-36.41, -62.95, Math.toRadians(90.00)))
        //.splineTo(new Vector2d(-36.41, -33.29), Math.toRadians(90.27))
        //.build();
        //drive.setPoseEstimate(centerToPixel.start());
            //Away from Pixel and To Backstage
        //TrajectorySequence centerToPixel = drive.trajectorySequenceBuilder(new Pose2d(-36.41, -33.29, Math.toRadians(90.27)))
        //.splineTo(new Vector2d(-54.50, -47.83), Math.toRadians(88.36))
        //.splineTo(new Vector2d(-51.68, -12.23), Math.toRadians(85.19))
        //.splineTo(new Vector2d(60.88, -12.68), Math.toRadians(-2.64))
        //.build();
        //drive.setPoseEstimate(centerToPixel.start());

        //----RIGHT MOVEMENTS----//
            //To Pixel Drop
        //TrajectorySequence rightToPixel = drive.trajectorySequenceBuilder(new Pose2d(-36.56, -62.95, Math.toRadians(90.00)))
        //.splineTo(new Vector2d(-31.96, -36.26), Math.toRadians(19.87))
        //.build();
        //drive.setPoseEstimate(rightToPixel.start());
            //Away from Pixel and To Backstage
        //TrajectorySequence rightToBackStage = drive.trajectorySequenceBuilder(new Pose2d(-31.96, -36.26, Math.toRadians(19.87)))
        //.splineTo(new Vector2d(-54.06, -34.48), Math.toRadians(62.62))
        //.splineTo(new Vector2d(-43.08, -12.09), Math.toRadians(64.06))
        //.splineTo(new Vector2d(59.25, -12.83), Math.toRadians(-6.10))
        //.build();
        //drive.setPoseEstimate(rightToBackStage.start());

    }
}
