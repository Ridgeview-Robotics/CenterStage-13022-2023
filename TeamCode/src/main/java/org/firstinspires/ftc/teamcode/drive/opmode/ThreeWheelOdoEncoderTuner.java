package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.Encoder;

@Autonomous
public class ThreeWheelOdoEncoderTuner extends LinearOpMode {

    Encoder left, right, perp;
    @Override
    public void runOpMode( ) throws InterruptedException {
        left = new Encoder( hardwareMap.get( DcMotorEx.class, "left_back_drive" ) );
        right = new Encoder( hardwareMap.get( DcMotorEx.class, "intake" ) );
        perp = new Encoder( hardwareMap.get( DcMotorEx.class, "right_back_drive" ) );


        waitForStart();

        while( opModeIsActive() ) {
            telemetry.addData( "left pos", left.getCurrentPosition() );
            telemetry.addData( "right pos", right.getCurrentPosition() );
            telemetry.addData( "perp pos", perp.getCurrentPosition() );
            telemetry.update();
        }
    }
}