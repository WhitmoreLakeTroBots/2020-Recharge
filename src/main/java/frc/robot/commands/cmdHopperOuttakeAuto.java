/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class cmdHopperOuttakeAuto extends Command {
  private boolean _isFinished;
  public cmdHopperOuttakeAuto() {
    requires(Robot.subHopperIntake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.subHopperIntake.hopperStopper();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.subHopperIntake.hopperIntakeIn();
    try {
      TimeUnit.MILLISECONDS.sleep(650);
  } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
  }
    Robot.subHopperIntake.hopperOuttake();
    try {
      TimeUnit.MILLISECONDS.sleep(1500);
  } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
  }
  _isFinished = true;
  
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subHopperIntake.hopperStopper();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}