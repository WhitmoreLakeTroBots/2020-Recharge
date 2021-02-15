// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBangNoStop extends CommandGroup {
  /** Add your docs here. */
  public autoGroupBangNoStop() {
    double speedSlow = 0.2;
    double speedMed = 0.2;
    double speedFast = 0.2;

    // set a speed just fast enough to not be coasting
    double speedBrake = -0.01;

    // Drive straight to pull out of the start box
    addSequential(new Auto_BangDriveByGyroNoStop(10, speedSlow, 0));

    // Swing turn to the left
    addSequential(new Auto_BangTurnByGyroNoStop(speedBrake, speedSlow, -70));

    // Drive forward
    addSequential(new Auto_BangDriveByGyroNoStop(65, speedMed, -70));

    // Swing turn to the right back to heading of 0
    addSequential(new Auto_BangTurnByGyroNoStop(speedSlow, speedBrake, 0));

    // Drive fast and stright to the other end of the field
    addSequential(new Auto_BangDriveByGyroNoStop(148, speedFast, 10));

    // Stop NOW to advoid disaster
    addSequential(new Auto_BangStop());
  }
}
