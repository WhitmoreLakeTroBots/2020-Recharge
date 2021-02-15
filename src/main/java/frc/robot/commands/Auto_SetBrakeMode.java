package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Auto_SetBrakeMode extends Command {
  private IdleMode _newMode = IdleMode.kBrake;
  private boolean _isFinished = false;

  public Auto_SetBrakeMode(IdleMode newMode) {
    requires(Robot.subChassis);
    _newMode = newMode;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.subChassis.setBrakeMode(_newMode);
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