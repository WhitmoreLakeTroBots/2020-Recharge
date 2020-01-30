/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.OI;
import frc.robot.PidConstants;
import frc.robot.Robot;
import frc.robot.Settings;
import frc.robot.motion_profile.MotionProfiler;
import frc.robot.subsystems.subLimelight.LED_MODE;

public class cmdTurnByLimeLight extends Command {
  public boolean _isFinished = false;
  private double angleDividend = 0.1;
  private double angleTol = 0.5;
  public double angleErr;
  private double _endTime;
  private double _turnSignum;
  //private double _degrees;
  private double _distance;
  private double _cruiseSpeed;
  private double _accel;
	private double _startTime;
	private double _requestedHeading = 0;
	private double _distanceSignum;
  private double _absDistance;
  private double _abortTime;
  
  
  private MotionProfiler mp;  
  public cmdTurnByLimeLight() {
   // _degrees = turnAngle;
    _accel = Settings.profileDriveAccelration;
    _cruiseSpeed = 75;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  double _degrees = Robot.subLimelight.getTX() ;
   //double turnAngle = angleErr * angleDividend;
   
    Robot.subLimelight.setLEDMode(LED_MODE.ON);
    Robot.subLimelight.setPipeline(0);
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
    System.err.println("degrees "  + _degrees + " Distance : " + _distance);
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    double deltaTime = CommonLogic.getTime() - _startTime;
    double currentHeading = Robot.subGyro.getNormaliziedNavxAngle();
    double profileDist = mp.getTotalDistanceTraveled(deltaTime);
		//double turnValue = calcTurnRate(currentHeading);
    double profileVelocity = mp.getProfileCurrVelocity(deltaTime);
    double throttlePos = (profileVelocity / Settings.chassisMaxInchesPerSec);
    System.err.println("Profile Velocity: "+ profileVelocity + " Distance " + _absDistance + "  " + _turnSignum + "  " + deltaTime);

    
    double pidVal = 0;
    double finalThrottle = throttlePos + pidVal;


    Robot.subChassis.Drive(finalThrottle * _turnSignum  * 2, -finalThrottle * _turnSignum * 2);;
    
    if (deltaTime > _endTime) {
			_isFinished = true;
    }
    
  }
  /*protected double calcTurnRate(double currentHeading) {
    angleErr = Robot.subLimelight.getTX() - 2;
      
    double turnRate = CommonLogic.calcTurnRate(turnAngle, Robot.subChassis.driveStraightGyroKp);
        // turn rate must be expressed as RPMs
    return (turnRate * PidConstants.Chassis_teleOpMotionKs.maxRPM);
  }*/
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


  @Override
  protected void interrupted() {
    if(Robot.oi.joyDrive().getY() > 0.0){
      end();
    }
  }
}
