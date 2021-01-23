package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupShootAngleInvt extends CommandGroup {
  /**
   * Add your docs here.
   */
  public autoGroupShootAngleInvt() {
    addSequential(new cmdHopperIntakeMotor(), 6);
    addParallel(new cmdLauncher(), 10);
    addSequential(new cmdAutoShooter());
    //addSequential(new Auto_TurnByGyro(-45 ,10));
    addSequential(new Auto_DriveByGyro(100, 24, 0));
    
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
