package org.firstinspires.ftc.teamcode.Systems.Controller;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SamPID {

    // Data Fields
    private double pCoeff;
    private double iCoeff;
    private double dCoeff;
    private double sumError, prevError;
    private double prevPrevError;

    ElapsedTime turnTime = new ElapsedTime();

    // Constructor
    public SamPID(double pCoeff, double iCoeff, double dCoeff)
    {
        this.pCoeff = pCoeff;
        this.iCoeff = iCoeff;
        this.dCoeff = dCoeff;

        sumError = 0.0;
        prevError = 0.0;
    }

    // Methods
    double getSpeed(double error)
    {
        sumError += error;

        // Calculate Derivative (slope of the error)
        double dError = error - prevError;

        prevPrevError = prevError;

        prevError = error;

        return pCoeff*error + iCoeff*sumError + dCoeff*dError;
    }

    boolean checkTimeOut(double error)
    {
        if (Math.abs(error - prevPrevError) == 0 && error < 1000)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    boolean checkTimeOutGyro(double error)
    {
        turnTime.reset();
        double tolerance = 0.5;
        double timeOut = 2;


            if (error < tolerance)
            {
                return false;
            } else
            {
                return true;
            }



    }

    boolean checkTimeOutGyro2(double error, double timeOut)
    {
        double tolerance = 7;
        while(turnTime.seconds() < timeOut)
        {
            if (Math.abs(error - prevPrevError) == 0 && error < tolerance)
            {
                return false;
            } else
            {
                return true;
            }
        }

        return false;

    }

    double printStuff(double error)
    {
        return Math.abs(error - prevPrevError);
    }
}
