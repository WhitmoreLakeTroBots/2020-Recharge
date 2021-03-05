// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBounce extends CommandGroup {
  /** Add your docs here. */
  double speedSlow = 0.3;
  double speedMed = 0.6;
  double speedFast = 0.8;
  double insidek = .13;
  double outsidek = .5;
  public autoGroupBounce() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
    //bump 1
    addSequential(new Auto_BangDriveByGyro(16, speedMed, 0));
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));
    addSequential(new Auto_BangDriveByGyro(10, speedMed, -85));
    addSequential(new Auto_BangDriveByGyro(-96, -speedMed, -100));
    //turn to next trench
    //addSequential(new Auto_BangTurnByGyro(-insidek, -outsidek, 180));
    addSequential(new Auto_BangTurnByGyro(-insidek/1.5, -outsidek/1.5, 180));
    addSequential(new Auto_BangDriveByGyro(-12, -speedMed, 180));
    addSequential(new Auto_BangTurnByGyro(-insidek/1.5, -outsidek/1.5, 90));
    //bump 2
    addSequential(new Auto_BangDriveByGyro(-80, -speedMed, 90));
    
    //addSequential(new Auto_BangStop(), .5);
    //addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
    addSequential(new Auto_BangDriveByGyro(72, speedMed, 90));
    
    //turn to final hit
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 0));
    addSequential(new Auto_BangDriveByGyro(18, speedMed, 0));
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -60));
    
    //final bump
    addSequential(new Auto_BangDriveByGyro(80, speedMed, 90));
    addSequential(new Auto_BangTurnByGyro(-insidek, -outsidek, 180));
    //addSequential(new Auto_BangDriveByGyro(12, -speedMed, 155));
    addSequential(new Auto_BangStop());
    addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
    
  }
}
