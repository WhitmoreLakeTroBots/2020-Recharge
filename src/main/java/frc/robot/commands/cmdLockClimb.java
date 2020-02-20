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

public class cmdLockClimb extends Command {
  private boolean _isFinished;
  public cmdLockClimb() {
    requires(Robot.subClimb);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Robot.subClimb.releasEncoder.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.subClimb.lockClimb();
    _isFinished = CommonLogic.isInRange(Robot.subClimb.releasEncoder.getDistance(), 40, 30);
    System.err.println("ENCODER COUNT" + Robot.subClimb.releasEncoder.getDistance());
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subClimb.stopExtend();
    System.err.println("CLIMB IS LOCKED");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
