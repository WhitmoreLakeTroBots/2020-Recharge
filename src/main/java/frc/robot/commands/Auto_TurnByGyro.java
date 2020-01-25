/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.PidConstants;
import frc.robot.Robot;
import frc.robot.Settings;
import frc.robot.motion_profile.MotionProfiler;

public class Auto_TurnByGyro extends Command {
  private double _degrees;
  private double _distance;
  private double _cruiseSpeed;
  private double _accel;
	private boolean _isFinished = false;
	private double _startTime;
	private double _requestedHeading = 0;
	private double _distanceSignum;
	private double _absDistance;
	private double _abortTime;
  private double _endTime;
  private double _turnSignum;

  private MotionProfiler mp;
  public Auto_TurnByGyro(double deg, double cruiseSpeed){
    _degrees = deg;
    _accel = Settings.profileDriveAccelration;
    _cruiseSpeed = cruiseSpeed;
  }

  public Auto_TurnByGyro(double deg, double cruiseSpeed, double accel) {
    _degrees = deg;
    _accel = accel;
    _cruiseSpeed = cruiseSpeed;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    System.err.println("Auto_TurnByGyro.initialize()");
    _turnSignum = Math.signum(_degrees);
    // start the motion
    Robot.subChassis.resetEncoder_LeftDrive();
    Robot.subChassis.resetEncoder_RightDrive();
    _distance = CommonLogic.calcArcLength (_degrees, Robot.subChassis.trackRadius) ;
    _absDistance = Math.abs(_distance);
    _distanceSignum = Math.signum(_distance);
    _abortTime = _absDistance / _cruiseSpeed;
    mp = new MotionProfiler(_absDistance, 0, _cruiseSpeed, _accel);
    _endTime = mp._stopTime * Settings.profileEndTimeScalar;
    _startTime = CommonLogic.getTime();
    _isFinished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
   //double encoderVal = Robot.subChassis.getEncoderAvgDistInch();
		double deltaTime = CommonLogic.getTime() - _startTime;
    //double profileDist = mp.getTotalDistanceTraveled(deltaTime);
    double currentHeading = Robot.subGyro.getNormaliziedNavxAngle();
    double profileDist = mp.getTotalDistanceTraveled(deltaTime);
		double turnValue = calcTurnRate(currentHeading);
    double profileVelocity = mp.getProfileCurrVelocity(deltaTime);
    double throttlePos = (profileVelocity / Settings.chassisMaxInchesPerSec);
    System.err.println("Profile Velocity: "+ profileVelocity + " Distance " + _absDistance + "  " + _turnSignum + "  " + deltaTime);

    //double leftRPM = Robot.subChassis.inches_sec2RPM(profileVelocity - turnValue);
    //double rightRPM = Robot.subChassis.inches_sec2RPM(profileVelocity + turnValue);
    double pidVal = 0;
    double finalThrottle = throttlePos + pidVal;
    Robot.subChassis.Drive(finalThrottle * _turnSignum, -finalThrottle * _turnSignum);

    // see if we are really done with the move... call Tolerance as 1% of _distance
    //if (CommonLogic.isInRange(Robot.subChassis.getEncoder_Inches_LR(), _distance, (_distance * .01))) {
    //  _isFinished = true;
    // }

    // fail safe we end if time expires
    if (deltaTime > _endTime) {
			_isFinished = true;
    }
    
   
  }

  protected double calcTurnRate(double currentHeading) {
    
    double turnRate = CommonLogic.calcTurnRate(
        Robot.subGyro.deltaHeading(Robot.subGyro.getNormaliziedNavxAngle(), _requestedHeading),
        Robot.subChassis.driveStraightGyroKp);
    
        // turn rate must be expressed as RPMs
    return (turnRate * PidConstants.Chassis_teleOpMotionKs.maxRPM);
    
  }
  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subChassis.Drive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
