// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBangBarrel extends CommandGroup {
  /** Add your docs here. */

  double speedSlow = 0.3;
  double speedMed = 0.80;
  double speedFast = 1;
  double insidek = .11 -.02;//inside
  double outsidek = .55 + .1;//outsude
                             //USA
  public autoGroupBangBarrel() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
  addSequential(new Auto_BangDriveByGyro(82, speedMed+.08, 0, 12));
  //loop one
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 0));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 90));;
      addSequential(new Auto_BangDriveByGyro(10, speedMed, 90));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 180));
      addSequential(new Auto_BangDriveByGyro(15, speedMed, 180));
  addSequential(new Auto_BangTurnByGyro(insidek, outsidek, -23));
  
  addSequential(new Auto_BangDriveByGyro(84, speedMed+ .12, -20, 12));

  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));
      addSequential(new Auto_BangDriveByGyro(10, speedMed, 0));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 180));;
      addSequential(new Auto_BangDriveByGyro(18, speedMed, 180));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 90));
     addSequential(new Auto_BangDriveByGyro(10, speedMed, 0));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 65));
  
  addSequential(new Auto_BangDriveByGyro(75, speedMed+.1, 50, 12));

  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 0));
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));;
  addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -142));

  //addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 188));
  //addSequential(new Auto_SetNavX_Invert(true));
  addSequential(new Auto_BangDriveByGyro(270, speedFast, 180));
  addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
  
  }
}
