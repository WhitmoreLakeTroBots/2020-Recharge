// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupGyroTest extends CommandGroup {
  /** An auto testing program that is to keep the brakes
   * off and allow the robot to "coast" between moves
   * The hope is that physically it is not stopping between
   * move segments and is thus smooth accurate and fast.
   */
  public autoGroupGyroTest() {
    double speedSlow = 0.2;
    double speedMed = 0.4;
    double speedFast = 0.7;
    
    // set a speed just fast enough to not be coasting
    double speedBrake = -0.01;

    // set to coast mode between segments
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));

    // Drive straight to pull out of the start box
    addSequential(new Auto_TestGyro());
    System.err.println("Running test");

    // Stop NOW to advoid disaster
    addSequential(new Auto_BangStop());
    System.err.println("Stopping Test");
    
    // turn On Brakes
    addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
  }
}
