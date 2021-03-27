// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class autoGroupdecideballrun extends CommandGroup {
  /** Add your docs here. */
  public autoGroupdecideballrun() {
   if(Robot.subLimelight.getTX2() != 0){
     addSequential(new autoGroupRedBall());
   }else {
     addSequential(new Auto_BangTurnByGyro(-.3, .3, 90));
     addSequential(new Auto_BangTurnByGyro(.3, -.3, 0));
   }
  }
}
