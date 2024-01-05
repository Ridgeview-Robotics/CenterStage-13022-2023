package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class FirstSplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        waitForStart();

        if(isStopRequested()) return;

        TrajectorySequence untitled0 = drive.trajectorySequenceBuilder(new Pose2d(10.90, -61.75, Math.toRadians(90.00)))
                .splineTo(new Vector2d(11.27, -12.95), Math.toRadians(89.56))
                .splineTo(new Vector2d(-66.60, -11.64), Math.toRadians(179.04))
                .build();
        drive.setPoseEstimate(untitled0.start());



        drive.followTrajectorySequence(untitled0);

    }
}
