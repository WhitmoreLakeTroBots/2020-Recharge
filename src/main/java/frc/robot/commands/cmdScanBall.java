

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Settings;

public class cmdScanBall extends Command {
  private boolean _isFinished = false;
  public cmdScanBall() {
    //private double scanTimes;
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.subLimelight.setPipelinelow(2);
    Settings.powerCount = 0;
  }
    
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  if(Robot.subLimelight.getLTV2() == 0){
    _isFinished = false;
  }
  else{
    _isFinished = true;
    Settings.powerCount = 1;
  }
}
  

  @Override
  protected boolean isFinished() {
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {}
  
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
