package frc.robot;
public class CommonLogic extends Object {

    public static final double joyDeadBand(double joy, double deadband){

        double retValue = joy;  
        if (Math.abs(joy) < Math.abs(deadband)) {
            joy = 0;
        }
        return Math.pow(retValue,2);
    }

}