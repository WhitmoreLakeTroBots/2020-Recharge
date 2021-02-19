package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Auto_SetNavX_Invert extends Command {
  private boolean _isFinished = false;
  private boolean _newValue = true;
  
  public Auto_SetNavX_Invert(boolean newValue) {
    requires(Robot.subChassis);
    _newValue = newValue;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.subGyro.invertNavx(_newValue);
    end();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    _isFinished = true;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}