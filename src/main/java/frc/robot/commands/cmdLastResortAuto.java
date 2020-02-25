/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class cmdLastResortAuto extends CommandGroup {
  /**
   * Add your docs here.
   */
  public cmdLastResortAuto() {
    addSequential(new Auto_DriveByGyro(100, 45, 0));
    addParallel(new cmdHopperDump());
    addSequential(new Auto_TurnByGyro(90, 24));
    addSequential(new Auto_DriveByGyro(36, 30, 0));
    addSequential(new Auto_TurnByGyro(-90, 24));
    addSequential(new cmdHopperOuttakeMotor(), 2);
    addSequential(new Auto_TurnByGyro(135, 30));
    addSequential(new Auto_DriveByGyro(130, 50, 0));
    



    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
