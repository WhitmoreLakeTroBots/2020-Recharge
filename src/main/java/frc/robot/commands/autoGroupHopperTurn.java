/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupHopperTurn extends CommandGroup {
  /**
   * Add your docs here.
   */
  public autoGroupHopperTurn() {

    
    addParallel(new cmdHopperDump());
    addSequential(new Auto_DriveByGyro(104, 40, 0));
    addSequential(new cmdDelay(1));
    addSequential(new cmdGroupHopperOuttake());
    addSequential(new cmdHopperOuttakeMotor(), 2);
    addSequential(new Auto_TurnByGyro(30, 30));
    addSequential(new Auto_DriveByGyro(-120, 55, 0));
    
    //addSequential(new cmdHopperOuttakeMotor());
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

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
