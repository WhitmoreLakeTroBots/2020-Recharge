// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class cmdFlywheelSpeedThree extends Command {
  public cmdFlywheelSpeedThree() {
    requires(Robot.subLauncher);
  }

  @Override
  protected void initialize() {
    Robot.subLauncher.flyWheelMotor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  protected void execute() {
    System.err.println("LAUNCHING");
    Robot.subLauncher.runFlyWheelSpeedThree();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subLauncher.flyWheelMotor.set(0);
  }
  
  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
