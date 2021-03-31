// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class cmdBallSight extends Command {
  public cmdBallSight() {
   
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {  
    Robot.subLimelight.setPipelinelow(2);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.subLimelight.getLTV2() != 0.00){
      Robot.subIntake.intakeMotor.set(.2);
    }else {
      Robot.subIntake.intakeMotor.set(0);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {}

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {}
}
