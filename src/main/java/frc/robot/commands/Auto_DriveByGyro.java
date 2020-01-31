// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.Robot;
import frc.robot.Settings;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.State;
import frc.robot.PidConstants.Chassis_teleOpMotionKs;

public class Auto_DriveByGyro extends Command {

  private double _distance;
  private boolean _isFinished = false;
  private double _startTime;
  private double _requestedHeading = 0;
  private double _distanceSignum;
  private double _absDistance;
  private double _abortTime;

  private Constraints tpConstraints = new Constraints(0.0, 0.0);
  private ProfiledPIDController pPidC;

  /**
   * Accepting a the motor velocities for left and right sides of the robot to
   * allow command to steer the robot using the default accelleration.
   *
   * @param dist_inces    -- distance for the robot to move
   * @param velInches_sec -- velocity in inches per sec
   * @param heading_deg   -- desired heading in degrees
   **/
  public Auto_DriveByGyro(double dist_inches, double velInches_sec, double heading_deg) {
    requires(Robot.subChassis);
    _distance = dist_inches;
    _requestedHeading = heading_deg;
    tpConstraints.maxVelocity = Math.abs(velInches_sec);
    tpConstraints.maxAcceleration = Math.abs(Settings.profileDriveAccelration);
  }

  /**
   * Accepting the motor velocities for left and right sides of the robot to allow
   * command to steer the robot
   *
   * @param dist_inces    -- distance for the robot to move
   * @param velInches_sec -- velocity in inches per sec
   * @param heading_deg   -- desired heading in degrees
   * @param accel_sec_sec -- overide the accel with custom inches/sec/sec
   **/

  public Auto_DriveByGyro(double dist_inches, double velInches_sec, double heading_deg, double accel_sec_sec) {
    requires(Robot.subChassis);
    _distance = dist_inches;
    _requestedHeading = heading_deg;
    tpConstraints.maxVelocity = Math.abs(velInches_sec);
    tpConstraints.maxAcceleration = Math.abs(accel_sec_sec);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    Robot.subChassis.resetEncoder_LeftDrive();
    Robot.subChassis.resetEncoder_RightDrive();
    _absDistance = Math.abs(_distance);
    _distanceSignum = Math.signum(_distance);

    pPidC = new ProfiledPIDController(Chassis_teleOpMotionKs.kP, Chassis_teleOpMotionKs.kI, Chassis_teleOpMotionKs.kD,
        tpConstraints, .020);

    pPidC.setTolerance(Settings.profileEndTol);

    pPidC.setGoal(new State(_absDistance, tpConstraints.maxVelocity));
    _isFinished = false;
    _startTime = CommonLogic.getTime();
    _abortTime = CommonLogic.calcProfileAbortTime(_absDistance, tpConstraints.maxVelocity,
        tpConstraints.maxAcceleration);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double deltaTime = CommonLogic.getTime() - _startTime;
    double measurement = Math.abs(Robot.subChassis.getEncoder_Inches_LR());
    double finalThrottle = _distanceSignum * pPidC.calculate(measurement);
    double trunRate = 0; // calcTurnRate();
    System.err.println("fThrottle:" + finalThrottle + " measurment" + measurement);

    // drive finalThrottle speed and add/subtract turnRate to steer.
    Robot.subChassis.Drive(finalThrottle - trunRate, finalThrottle + trunRate);

    // Check to see if we are done....
    if (pPidC.atGoal()) {
      // The pPidC says we are done
      System.err.println("atGoal=true");
      _isFinished = true;
    }

    else if (CommonLogic.isInRange(measurement, _absDistance, Settings.profileEndTol)) {
      // Our own checks on distance traveled says we are done
      System.err.println("isInRange=true");
      _isFinished = true;
    }

    else if (deltaTime > _abortTime) {
      // abort if we are not finishing in time. (AKA we are close but not there yet.)
      System.err.println("exceeded abort time. " + _abortTime + " " + deltaTime);
      _isFinished = true;
    }
  }

  protected double calcTurnRate() {

    double turnRate = CommonLogic.calcTurnRate(Robot.subGyro.deltaHeading(_requestedHeading),
        Robot.subChassis.driveStraightGyroKp);
    return (turnRate);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.err.println("Auto_DriveByGyro.isFinished()= " + _isFinished);
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subChassis.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.subChassis.stop();
  }
}
