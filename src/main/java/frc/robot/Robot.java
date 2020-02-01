package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto_DriveByGyro;
import frc.robot.commands.Auto_TurnByGyro;
import frc.robot.commands.autoGroupShoot;
import frc.robot.subsystems.subChassis;
import frc.robot.subsystems.subClimb;
import frc.robot.subsystems.subControlPannel;
import frc.robot.subsystems.subGyro;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subLauncher;
import frc.robot.subsystems.subLimelight;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

  static Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();
  public static boolean isDriveInverted = false;

  public static OI oi;
  // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
  public static subChassis subChassis;
  public static subIntake subIntake;
  public static subClimb subClimb;
  public static subLauncher subLauncher;
  public static subControlPannel subControlPannel;
  public static subGyro subGyro;
  public static subLimelight subLimelight;
  

 
  
  // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    subChassis = new subChassis();
    subIntake = new subIntake();
    subClimb = new subClimb();
    subLauncher = new subLauncher();
    subControlPannel = new subControlPannel();
    subGyro = new subGyro();
    subLimelight = new subLimelight();


    

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    // OI must be constructed after subsystems. If the OI creates Commands
    // (which it very likely will), subsystems are not guaranteed to be
    // constructed yet. Thus, their requires() statements may grab null
    // pointers. Bad news. Don't move it.
    oi = new OI();

    HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);

    // Add commands to Autonomous Sendable Chooser
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    //chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());
    //chooser.setDefaultOption("Auto ", new autoGroupShoot());

    chooser.addOption("Auto Drive By Gyro", new Auto_DriveByGyro(36, 72, 0));
    //chooser.addOption("Auto Turn By Gyro", new Auto_TurnByGyro(90, 15));
    
    //chooser.setDefaultOption("Drive By Gyro", autonomousCommand);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
    SmartDashboard.putData("Auto mode", chooser);
  }

  /**
   * This function is called when the disabled button is hit. You can use it to
   * reset subsystems before shutting down.
   */
  @Override
  public void disabledInit() {

  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    //autonomousCommand = chooser.getSelected();
    autonomousCommand = chooser.getSelected();
    // schedule the autonomous command (example)
    if (autonomousCommand != null)
      autonomousCommand.start();
  }

  /**
   * This function is called periodically during autonomous
   */
  @Override
  public void autonomousPeriodic() {

    Scheduler.getInstance().run();
    
    
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null)
      autonomousCommand.cancel();
  }

  /**
   * This function is called periodically during operator control
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }
}