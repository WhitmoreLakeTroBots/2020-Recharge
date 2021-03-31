/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.subLauncher;

public class cmdLauncher extends Command {
  public cmdLauncher() {
   requires(Robot.subLauncher);
  }

  @Override
  protected void initialize() {
    Robot.subLauncher.flyWheelMotor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  protected void execute() {
    System.err.println("LAUNCHING");
    Robot.subLauncher.runFlyWheel();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    subLauncher.flyWheelMotor.set(0);
  }
  

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    
    end();
  }
}
