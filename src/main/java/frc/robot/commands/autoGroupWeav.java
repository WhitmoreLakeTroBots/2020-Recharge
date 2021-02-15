// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupWeav extends CommandGroup {
  /** Add your docs here. */
  public autoGroupWeav() {
    addSequential(new Auto_DriveByGyro(8, 18, 0));
    addSequential(new Auto_DriveByGyro(65 ,60 ,-80));
    addSequential(new Auto_DriveByGyro(160, 80, 0));
   // addSequential(new Auto_DriveByGyro(100, 50, +120));
    
  
  }
}
