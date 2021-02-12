// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupWeav extends CommandGroup {
  /** Add your docs here. */
  public autoGroupWeav() {
    addSequential(new Auto_DriveByGyro(10, 18, 0));
    addSequential(new Auto_DriveByGyro(65 ,80 ,-70));
    addSequential(new Auto_DriveByGyro(148, 80, 10));
    //addSequential(new Auto_DriveByGyro(60, velInches_sec, heading_deg));
  
  }
}
