package frc.robot.commands;

import java.util.Currency;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.Robot;

public class Auto_TestGyro extends Command {
  
  private boolean _isFinished = false;
  private double _currHeading = 0.0;
  private boolean _inTol = false;
  private double _startTime = 0.0;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.subChassis.setBrakeMode(IdleMode.kCoast);
    _startTime = CommonLogic.getTime();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    _currHeading = Robot.subGyro.getNormaliziedNavxAngle();
    _inTol = Robot.subGyro.gyroInTol(_currHeading, _currHeading, 2);
    double currTime = CommonLogic.getTime();
   
    //if ((currTime - _startTime) > 1000) {
      System.err.printf("Heading=%4.1f, InTol=%B\n", _currHeading, _inTol);
      //_startTime = currTime;
    //}
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subChassis.stop();
    Robot.subChassis.setBrakeMode(IdleMode.kBrake);
    _isFinished = true;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}