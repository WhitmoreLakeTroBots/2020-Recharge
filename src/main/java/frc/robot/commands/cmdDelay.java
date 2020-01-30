package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;

public class cmdDelay extends Command {

    double _delaySeconds = 0.0;
    double _startTime = 0.0;
    boolean _isFinished = false;

    public cmdDelay(int seconds) {
      _delaySeconds = (double) seconds;
    }

    public cmdDelay (double seconds) {
      _delaySeconds = seconds;
    }


    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
      _startTime = CommonLogic.getTime();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

      if ((_startTime + _delaySeconds) > CommonLogic.getTime()){
        _isFinished = true;
      }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return _isFinished;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
