package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupShootTrench extends CommandGroup {
  /**
   * Add your docs here.
   */
  public autoGroupShootTrench() {
    addParallel(new cmdLauncher(), 10);
    addSequential(new cmdAutoShooter());
    addSequential(new cmdCancelFlyWheel(), 1);
    
    //Drive to the trench
    addSequential(new Auto_TurnByGyro(150, 24, 0));
    addSequential(new Auto_DriveByGyro(100, 45, 0));
    addSequential(new Auto_TurnByGyro(30, 24, 0));
    

    //pickup balls on the trench
    addParallel(new cmdHopperIntake(), 5); 
    addParallel(new cmdIntakeRev(), 5);
    addSequential(new Auto_DriveByGyro(116, 40, 0));
    addSequential(new Auto_DriveByGyro(-116, 40, 0));

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
