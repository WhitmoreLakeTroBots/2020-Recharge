// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBounce extends CommandGroup {
  /** Add your docs here. */
  double speedSlow = 0.3;
  double speedMed = 0.5;
  double speedFast = 0.8;
  double insidek = .13;
  double outsidek = .5;
  public autoGroupBounce() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
    //bump 1
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, -90));
    addSequential(new Auto_BangDriveByGyro(-96, speedMed, -110));
    //turn to next trench
    addSequential(new Auto_BangTurnByGyro(-insidek, -outsidek, 180));
    addSequential(new Auto_BangTurnByGyro(-insidek, -outsidek, 90));
    //bump 2
    addSequential(new Auto_BangDriveByGyro(-120, speedMed, 90));
    addSequential(new Auto_BangStop(), .5);
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
    addSequential(new Auto_BangDriveByGyro(120, speedMed, 90));
    //turn to final hit
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 0));
    addSequential(new Auto_BangDriveByGyro(36, speedMed, 0));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 90));
    //final bump
    addSequential(new Auto_BangDriveByGyro(120, speedMed, 90));
    addSequential(new Auto_BangStop(), .5);
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
    addSequential(new Auto_BangTurnByGyro(-insidek, -outsidek, 180));
  }
}
