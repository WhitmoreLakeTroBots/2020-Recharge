/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class CommonLogic {

  public static double CapMotorPower(double MotorPower, double negCapValue, double posCapValue) {
    // logic to cap the motor power between a good range
    double retValue = MotorPower;

    if (MotorPower < negCapValue) {
      retValue = negCapValue;
    }

    if (MotorPower > posCapValue) {
      retValue = posCapValue;
    }

    return retValue;
  }

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

  public static double getTime() {
    return (System.nanoTime() / Math.pow(10, 9));
  }

  public static double calcTurnRate(double deltaHeading, double proportion) {

    double commandedTurnRate = deltaHeading * proportion;
    return commandedTurnRate; // IS ALWAYS POSITIVE!
  }
  public double deg2Rad(double degrees){
    return Math.toRadians(degrees);
  }

  public static double calcArcLength(double degrees, double radius){
    return (Math.toRadians(degrees) * radius);
  }

}
