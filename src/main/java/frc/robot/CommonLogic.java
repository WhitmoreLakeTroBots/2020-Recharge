package frc.robot;

public class CommonLogic extends Object {

    public static final double joyDeadBand(double joy, double deadband) {

        double retValue = joy;
        if (Math.abs(retValue) < Math.abs(deadband)) {
            retValue = 0;
        }
        return Math.pow(retValue, 2) * Math.signum(joy);
    }

    public static final boolean isInRange(double curRevs, double desiredRevs, double Tol) {

        double loVal = desiredRevs - Tol;
        double hiVal = desiredRevs + Tol;
        boolean retValue = false;

        if (curRevs > loVal && curRevs < hiVal) {
            retValue = true;
        }
        return retValue;
    }
    
}