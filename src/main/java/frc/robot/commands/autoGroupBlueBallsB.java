// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupBlueBallsB extends CommandGroup {
  /** Add your docs here. */
  double speedSlow = 0.3;
  double speedMed = 0.6;
  double speedFast = 0.8;
  double insidek = -.13;
  double outsidek = .5+.06;
  public autoGroupBlueBallsB() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
/*

            WE ARE SET UP ON AN ANGLE! NOTE THAT ZERO IS ABOUT 24 degrees 
*/
    addParallel(new cmdHopperIntake());
    addParallel(new cmdIntakeRev(), 6.5);
    addParallel(new cmdHopperIntakeMotor(), 6.7);
    addSequential(new Auto_BangDriveByGyro(60, speedFast, 0));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 64));
    addSequential(new Auto_BangDriveByGyro(78, speedMed, 60 ));
    addSequential(new Auto_BangTurnByGyro(.5, -.4, -15));
    addSequential(new Auto_BangDriveByGyro(95, speedMed, -21));
    addSequential(new Auto_BangTurnByGyro(-.4, .5, 80));
    addSequential(new Auto_BangDriveByGyro(80, speedMed, 80));
    addSequential(new Auto_BangTurnByGyro(.7,-.6, -10));
    addSequential(new Auto_BangDriveByGyro(40, speedFast, 18));
    addSequential(new Auto_BangStop());


  }
}
