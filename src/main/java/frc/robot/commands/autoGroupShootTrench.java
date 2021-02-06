package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupShootTrench extends CommandGroup {
 
  public autoGroupShootTrench() {
    addParallel(new cmdLauncher(), 10);
    addSequential(new cmdAutoShooter());
   addSequential(new cmdCancelFlyWheel(), 1);
    //Drive to the trench
    addSequential(new Auto_TurnByGyro(110, 28, 30));
    addSequential(new Auto_DriveByGyro(85, 45, 0));
    addSequential(new Auto_TurnByGyro(32, 28, 30));
    //pickup balls on the trench
    addParallel(new cmdHopperIntake(), 9);
    addParallel(new cmdHopperIntakeMotor(), 9); 
    addParallel(new cmdIntakeRev(), 11);
    addSequential(new Auto_DriveByGyro(124, 30, 0));
    //addSequential(new Auto_DriveByGyro(-118, 24, 0));

  }
}
