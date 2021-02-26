// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBangBarrel extends CommandGroup {
  /** Add your docs here. */

  double speedSlow = 0.3;
  double speedMed = 0.5;
  double speedFast = 0.8;
  double insidek = .13;
  double outsidek = .5;
  public autoGroupBangBarrel() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
  addSequential(new Auto_BangDriveByGyro(102, speedMed, 0, 12));
  //loop one
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 0));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 90));;
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 180));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, -20));
  
  addSequential(new Auto_BangDriveByGyro(110, speedMed, -20, 12));

  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 180));;
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 90));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 55));
  
  addSequential(new Auto_BangDriveByGyro(96  , speedMed, 55, 12));

  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 0));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));;
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 172));
  addSequential(new Auto_BangDriveByGyro(230, speedSlow, 180));
  addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
  
  }
}
