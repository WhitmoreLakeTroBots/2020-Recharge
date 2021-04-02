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
    addSequential(new Auto_BangDriveByGyro(68, speedFast, 0));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 68));
    addSequential(new Auto_BangDriveByGyro(74, speedMed, 66 ));
    addSequential(new Auto_BangTurnByGyro(.5, -.4, -12));
    addSequential(new Auto_BangDriveByGyro(95, speedMed, -8));
    addSequential(new Auto_BangTurnByGyro(-.4, .5, 64));
    addSequential(new Auto_BangDriveByGyro(86, speedMed, 64));
    addSequential(new Auto_BangStop());
    addSequential(new Auto_BangTurnByGyro(-.8, -.02, 179));
    addSequential(new Auto_BangDriveByGyro(20, speedMed, 179));


  }
}
