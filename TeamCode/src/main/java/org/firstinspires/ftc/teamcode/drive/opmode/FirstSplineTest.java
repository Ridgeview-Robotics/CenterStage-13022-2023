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

        TrajectorySequence path1 = drive.trajectorySequenceBuilder(new Pose2d(11.05, -61.03, Math.toRadians(90.00)))
                .splineTo(new Vector2d(14.01, -9.42), Math.toRadians(86.71))
                .splineTo(new Vector2d(-59.84, -11.05), Math.toRadians(181.27))
                .build();
        drive.setPoseEstimate(path1.start());



        drive.followTrajectorySequence(path1);

    }
}
