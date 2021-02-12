// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBangSix extends CommandGroup {
  /** Add your docs here. */
  public autoGroupBangSix() {

    addSequential(new Auto_BangDriveByGyro(72, .4, 0));



  }
}
