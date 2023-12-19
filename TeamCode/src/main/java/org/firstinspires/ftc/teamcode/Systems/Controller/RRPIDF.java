package org.firstinspires.ftc.teamcode.Systems.Controller;

public class RRPIDF {

        double Kp, Ki, Kd, Kf;
        double setPoint; //target location
        double processValue; //current input location

        double ce; //current error

        double tolerance =0.1;

        public RRPIDF(double kP, double kI, double kD, double kF, double sp, double pv){
                Kp = kP;
                Ki = kI;
                Kd = kD;
                Kf = kF;

                setPoint = sp;
                processValue = pv;

                ce = sp - pv;  // current error
        }

        public void setSetPoint(double sp){
                setPoint = sp;
                ce = setPoint - processValue;
        }

        public boolean atSetPoint(){
                return Math.abs(ce) < tolerance;
        }
}
