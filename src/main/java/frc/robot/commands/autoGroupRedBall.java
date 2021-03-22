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
  double insidek = .13;
  double outsidek = .5;
  public autoGroupRedBall() {
    addSequential(new Auto_SetBrakeMode(IdleMode.kCoast));
/*

            WE ARE SET UP ON AN ANGLE! NOTE THAT ZERO IS ABOUT 24 degrees 
*/
    addParallel(new cmdHopperIntake());
    addParallel(new cmdIntakeRev(), 7);
    addParallel(new cmdHopperIntakeMotor(), 7);
    addSequential(new Auto_BangDriveByGyro(66, speedSlow, 0));
    addSequential(new Auto_BangTurnByGyro(insidek, outsidek, 90));
    addSequential(new Auto_BangDriveByGyro(62, speedSlow, 90));
    addSequential(new Auto_BangTurnByGyro(.3, -.3, -21));
    addSequential(new Auto_BangDriveByGyro(96, speedSlow, -21));
    addSequential(new Auto_BangTurnByGyro(-.3, .3, 24));
    addSequential(new Auto_BangDriveByGyro(90, speedMed, 38));
    addSequential(new Auto_BangDriveByGyro(50, speedMed, 18));
    




  }

}
