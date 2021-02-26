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
    double speedSlow = 0.3;
    double speedMed = 0.5;
    double speedFast = 0.8;
    double insidek = .13;
    double outsidek = .5;
    
    // set a speed just fast enough to not be coasting
    double speedBrake = -0.01;

    // set to coast mode between segments
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));

    // Drive straight to pull out of the start box
    addSequential(new Auto_BangDriveByGyro(8, speedSlow, 0, 3));

    // Swing turn to the left
    addSequential(new Auto_BangTurnByGyro(outsidek, insidek, -70));

    // Drive forward
    addSequential(new Auto_BangDriveByGyro(12, speedMed, -70));

    // Swing turn to the right back to heading of 0
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 0));

    // Drive fast and stright to the other end of the field
    addSequential(new Auto_BangDriveByGyro(98, speedFast, 0));

    // Turn before full loop

    addSequential(new Auto_BangTurnByGyro(speedBrake, speedMed, 70));
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
