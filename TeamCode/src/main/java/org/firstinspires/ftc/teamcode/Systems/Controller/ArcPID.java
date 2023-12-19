package org.firstinspires.ftc.teamcode.Systems.Controller;

public class ArcPID extends ArcPIDF {

    /**
     * for use of 13022 not mine
     * Default constructor with just the coefficients
     */
    public ArcPID(double kp, double ki, double kd) {
        super(kp, ki, kd, 0);
    }

    /**
     * The extended constructor.
     */
    public ArcPID(double kp, double ki, double kd, double sp, double pv) {
        super(kp, ki, kd, 0, sp, pv);
    }

    public void setPID(double kp, double ki, double kd) {
        setPIDF(kp, ki, kd, 0);
    }

}
