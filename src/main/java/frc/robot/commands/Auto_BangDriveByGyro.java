package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.Robot;

public class Auto_BangDriveByGyro extends Command {

  private double _distance;
  private boolean _isFinished = false;
  private double _requestedHeading = 0;
  private double _absDistance;

  private double _throttle;
  private double _absTol_inches = 6.0;

  /**
   * Accepting a the motor velocities for left and right sides of the robot to
   * allow command to steer the robot using the default accelleration.
   *
   * @param dist_inches   -- distance for the robot to move
   * @param velInches_sec -- velocity in inches per sec
   * @param heading_deg   -- desired heading in degrees
   **/
  public Auto_BangDriveByGyro(double dist_inches, double throttle, double heading_deg) {
    requires(Robot.subChassis);
    _throttle = throttle;
    _distance = dist_inches;
    _requestedHeading = heading_deg;
  }

  /**
   * Accepting a the motor velocities for left and right sides of the robot to
   * allow command to steer the robot using the default accelleration.
   *
   * @param dist_inches   -- distance for the robot to move
   * @param velInches_sec -- velocity in inches per sec
   * @param heading_deg   -- desired heading in degrees
   * @param tol_inches    -- tolerance in inches
   **/

  public Auto_BangDriveByGyro(double dist_inches, double throttle, double heading_deg, double tol_inches) {
    requires(Robot.subChassis);
    _throttle = throttle;
    _distance = dist_inches;
    _requestedHeading = heading_deg;
    _absTol_inches = Math.abs(tol_inches);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    Robot.subChassis.resetEncoder_LeftDrive();
    Robot.subChassis.resetEncoder_RightDrive();
    _absDistance = Math.abs(_distance);
    _isFinished = false;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double measurement = Math.abs(Robot.subChassis.getEncoder_Inches_LR());
    double trunRate = calcTurnRate();
    // drive finalThrottle speed and add/subtract turnRate to steer.
    Robot.subChassis.Drive(_throttle - trunRate, _throttle + trunRate);

    // Check to see if we are done....

    if (measurement > (_absDistance - _absTol_inches)) {
      end();
      // Our own checks on distance traveled says we are done
      System.err.println("isInRange=true");
    }
  }

  protected double calcTurnRate() {

    double commandedTurnRate = Robot.subGyro.deltaHeading(_requestedHeading) * 3.5e-3;
    return commandedTurnRate; // IS ALWAYS POSITIVE!


  /*  double turnRate = CommonLogic.calcTurnRate(Robot.subGyro.deltaHeading(_requestedHeading),
        Robot.subChassis.driveStraightGyroKp);
    return (turnRate);
*/ 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // System.err.println("Auto_DriveByGyro.isFinished()= " + _isFinished);
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subChassis.stop();
    _isFinished = true;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
