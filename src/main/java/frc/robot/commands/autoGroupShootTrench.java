package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class autoGroupShootTrench extends CommandGroup {
 
  public autoGroupShootTrench() {
    addParallel(new cmdLauncher(), 10);
    addSequential(new cmdAutoShooter());
    addSequential(new cmdCancelFlyWheel(), 1);
    //Drive to the trench
    addSequential(new Auto_TurnByGyro(120, 28, 30));
    addSequential(new Auto_DriveByGyro(100, 50, 0));
    addSequential(new Auto_TurnByGyro(35, 28, 30));
    //pickup balls on the trench
    addParallel(new cmdHopperIntake(), 8);
    addParallel(new cmdHopperIntakeMotor(), 8); 
    addParallel(new cmdIntakeRev(), 10);
    addSequential(new Auto_DriveByGyro(118, 30, 0));
    addSequential(new Auto_DriveByGyro(-118, 34, 0));

  }
}
