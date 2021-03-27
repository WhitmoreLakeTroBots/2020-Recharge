// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;



import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupRedBall extends CommandGroup {
  /** Add your docs here. */
  double speedSlow = 0.3;
  double speedMed = 0.6;
  double speedFast = 0.8;
  double insidek = -.13;
  double outsidek = .5+.06;
  public autoGroupRedBall() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
/*

            WE ARE SET UP ON AN ANGLE! NOTE THAT ZERO IS ABOUT 24 degrees 
*/
    addParallel(new cmdHopperIntake());
    addParallel(new cmdIntakeRev(), 5.5);
    addParallel(new cmdHopperIntakeMotor(), 5.7);
    addSequential(new Auto_BangDriveByGyro(40, speedMed, 0));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 68));
    addSequential(new Auto_BangDriveByGyro(56, speedMed, 58));
    addSequential(new Auto_BangTurnByGyro(.5, -.4, -10));
    addSequential(new Auto_BangDriveByGyro(90, speedMed, -10));
    addSequential(new Auto_BangTurnByGyro(-.4, .5, 24));
    addSequential(new Auto_BangDriveByGyro(90, speedFast, 44));
    addSequential(new Auto_BangDriveByGyro(18, speedFast, 22));
    addSequential(new Auto_BangStop());
    




  }

}
