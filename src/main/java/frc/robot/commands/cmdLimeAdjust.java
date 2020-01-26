/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.subLimelight.LED_MODE;

public class cmdLimeAdjust extends Command {
  private boolean _isFInished;
  private double angleDividend = 0.1;
  private double angleTol = 0.5;
  public double angleErr;
  
  public cmdLimeAdjust() {
  
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.subLimelight.setLEDMode(LED_MODE.ON);
    Robot.subLimelight.setPipeline(0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    angleErr = Robot.subLimelight.getTX() - 8;
    double turnAngle = angleErr * angleDividend;
    new Auto_TurnByGyro(turnAngle, 24);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _isFInished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
