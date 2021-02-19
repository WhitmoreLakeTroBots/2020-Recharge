// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupCirclePractice extends CommandGroup {
  /** Add your docs here. */
  public autoGroupCirclePractice() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
    addSequential(new Auto_BangTurnByGyro(0.35, 0.09, 180));
    addSequential(new Auto_BangTurnByGyro(0.35, 0.09, 0));
    addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
  }
}
