package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupShootTrench extends CommandGroup {
 
  public autoGroupShootTrench() {
    addParallel(new cmdLauncher(), 10);
    addSequential(new cmdAutoShooter());
    addSequential(new cmdCancelFlyWheel(), 1);
    //Drive to the trench
    addSequential(new Auto_TurnByGyro(110, 10, 0));
    addSequential(new Auto_DriveByGyro(100, 45, 0));
    addSequential(new Auto_TurnByGyro(30, 24, 0));
    //pickup balls on the trench
    addParallel(new cmdHopperIntake(), 5); 
    addParallel(new cmdIntakeRev(), 5);
    addSequential(new Auto_DriveByGyro(116, 40, 0));
    addSequential(new Auto_DriveByGyro(-116, 40, 0));

  }
}
