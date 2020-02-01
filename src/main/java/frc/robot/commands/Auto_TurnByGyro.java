/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.Robot;
import frc.robot.Settings;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.State;
import frc.robot.PidConstants.Chassis_teleOpMotionKs;

public class Auto_TurnByGyro extends Command {
  private double _degrees;
  private double _gyroOffset;
  private double _distance;
  private boolean _isFinished = false;
	private double _startTime;
	private double _absDistance;
	private double _abortTime;
  
  private double _turnSignum;
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
  public Auto_TurnByGyro(double deg, double velInch_sec){
    requires(Robot.subChassis);
    _degrees = deg;
    tpConstraints.maxVelocity = Math.abs(velInch_sec);
    tpConstraints.maxAcceleration = Math.abs(Settings.profileDriveAccelration);
  }

  /**
   * Accepting the motor velocities for left and right sides of the robot to allow
   * command to steer the robot
   *
   * @param deg           -- Number of degress to turn  + is righthand turn - is lefthand turn
   * @param velInches_sec -- velocity in inches per sec
   * @param accel_sec_sec -- overide the accel with custom inches/sec/sec
   **/
  public Auto_TurnByGyro(double deg, double velInch_sec, double accel_inch_sec_sec) {
    _degrees = deg;
    tpConstraints.maxVelocity = Math.abs(velInch_sec);
    tpConstraints.maxAcceleration = Math.abs(accel_inch_sec_sec);
    // Use requires() here to declare subsystem dependencies
    requires(Robot.subChassis);
    }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    _gyroOffset = Robot.subGyro.getNavxAngleRaw();
    _turnSignum = Math.signum(_degrees);
    Robot.subChassis.resetEncoder_LeftDrive();
    Robot.subChassis.resetEncoder_RightDrive();
    _distance = CommonLogic.calcArcLength (_degrees, Robot.subChassis.trackRadius) ;
    _absDistance = Math.abs(_distance);
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
   //double encoderVal = Robot.subChassis.getEncoderAvgDistInch();
    double deltaTime = CommonLogic.getTime() - _startTime;
    
    
    double turnAngle = Robot.subGyro.getNavxAngleRaw() - _gyroOffset;                      
    double measurement = Math.abs (CommonLogic.calcArcLength(turnAngle, Robot.subChassis.trackRadius));                      
    //measurement = (Math.abs(Robot.subChassis.getEncoderPosLeft_Inches()) + 
    //               Math.abs(Robot.subChassis.getEncoderPosRight_Inches())) / 2;

    double finalThrottle = _turnSignum * pPidC.calculate(measurement);
    System.err.println("fThr:" + finalThrottle + " mea:" + measurement + " turnAngle:" + turnAngle);

    // drive finalThrottle speed and add/subtract turnRate to steer.
    Robot.subChassis.Drive(-1 * finalThrottle, finalThrottle);

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

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //System.err.println("Auto_DriveByGyro.isFinished()= " + _isFinished);
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
    end();
  }
}
