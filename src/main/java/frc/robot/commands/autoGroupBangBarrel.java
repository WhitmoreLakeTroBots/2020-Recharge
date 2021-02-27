// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBangBarrel extends CommandGroup {
  /** Add your docs here. */

  double speedSlow = 0.3;
  double speedMed = 0.67;
  double speedFast = 0.84;
  double insidek = .11;
  double outsidek = .55;
  public autoGroupBangBarrel() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
  addSequential(new Auto_BangDriveByGyro(90, speedMed, 0, 12));
  //loop one
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 0));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 90));;
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 180));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, -23));
  
  addSequential(new Auto_BangDriveByGyro(84, speedMed, -23, 12));

  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 180));;
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 90));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 65));
  
  addSequential(new Auto_BangDriveByGyro(82, speedMed, 55, 12));

  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 0));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));;
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -147));

  //addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 188));
  //addSequential(new Auto_SetNavX_Invert(true));
  addSequential(new Auto_BangDriveByGyro(260, speedFast, 180));
  addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
  
  }
}
