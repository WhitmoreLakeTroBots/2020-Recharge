package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupShootTrench extends CommandGroup {
  /**
   * Add your docs here.
   */
  public autoGroupShootTrench() {
    addParallel(new cmdLauncher(), 8);
    addSequential(new cmdAutoShooter());
    addSequential(new cmdCancelFlyWheel());
    
    //completid shooting moving to trench
    addSequential(new Auto_DriveByGyro(-85, 24, 0));
    addSequential(new Auto_TurnByGyro(90, 10));
    addSequential(new Auto_DriveByGyro(65, 24, 0));
    addSequential(new Auto_TurnByGyro(90, 10));

    //At tranch moving to grab more balls
    addParallel (new cmdAutoPickup());
    addSequential (new Auto_DriveByGyro(80, 24, 0));
    addSequential (new Auto_TurnByGyro(180, 15));
    addSequential (new Auto_DriveByGyro(80, 24, 0));
    addSequential (new Auto_TurnByGyro(35, 10));   
    
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
