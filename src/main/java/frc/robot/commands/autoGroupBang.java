// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBang extends CommandGroup {
  /** An auto testing program that is to keep the brakes
   * off and allow the robot to "coast" between moves
   * The hope is that physically it is not stopping between
   * move segments and is thus smooth accurate and fast.
   */
  public autoGroupBang() {
    double speedSlow = 0.4;
    double speedMed = 0.5;
    double speedFast = 0.88;
    double insidek = .13-.06;
    double outsidek = .5+.2;
    
    // set a speed just fast enough to not be coasting
    double speedBrake = -0.01;

    // set to coast mode between segments
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));

    // Drive straight to pull out of the start box
    addSequential(new Auto_BangDriveByGyro(16, speedSlow, 0, 3));

    // Swing turn to the left
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -55));
    addSequential(new Auto_BangDriveByGyro(8, speedSlow, -55, 2));

    // Drive forward0
    addSequential(new Auto_BangDriveByGyro(12, speedMed, -55));

    // Swing turn to the right back to heading of -25
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, -25));

    // Drive fast and stright to the other end of the field
    addSequential(new Auto_BangDriveByGyro(102, speedFast, 0));

    // Turn before full loop

    addSequential(new Auto_BangTurnByGyro(speedBrake, speedMed, 55));
    addSequential(new Auto_BangDriveByGyro(18, speedMed, 90));

    //loop
   
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 0));
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -90));;
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 180));
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 90));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 180));
    addSequential(new Auto_BangDriveByGyro(95, speedFast, 180));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, -90));
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, 180));
    
    // Stop NOW to advoid disaster
    addSequential(new Auto_BangStop());
    
    // turn On Brakes
    addSequential(new Auto_SetBrakeMode(IdleMode.kBrake));
  }
}
