package frc.robot;
public class CommonLogic extends Object {

    public static final double joyDeadBand(double joy, double deadband){

        double retValue = joy;  
        if (Math.abs(retValue) < Math.abs(deadband)) {
            retValue = 0;
        }
        return Math.pow(retValue,2) * Math.signum(joy);
    }

}