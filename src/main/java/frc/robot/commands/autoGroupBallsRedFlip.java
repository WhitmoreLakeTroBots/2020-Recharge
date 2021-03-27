// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBallsRedFlip extends CommandGroup {
  /** Add your docs here. */
  double speedSlow = 0.3;
  double speedMed = 0.6;
  double speedFast = 0.8;
  double insidek = .13;
  double outsidek = .5;
  public autoGroupBallsRedFlip() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
    addParallel(new cmdHopperIntake());
    addParallel(new cmdIntakeRev(), 4);
    addParallel(new cmdHopperIntakeMotor(), 4);
    addSequential(new Auto_BangDriveByGyro(36, speedMed, 0));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 18));
    addSequential(new Auto_BangDriveByGyro(58, speedMed, 12));
    addSequential(new Auto_BangTurnByGyro(outsidek+.2, -insidek, -69));
    addSequential(new Auto_BangDriveByGyro(92, speedMed, -65));
    addSequential(new Auto_BangTurnByGyro(-insidek, outsidek, 10));
    addSequential(new Auto_BangDriveByGyro(150, speedFast, 1));
    addSequential(new Auto_BangStop());
    
  }
}
