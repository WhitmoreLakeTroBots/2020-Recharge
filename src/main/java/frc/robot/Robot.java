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
import frc.robot.commands.autoGroupBallsRedFlip;
import frc.robot.commands.autoGroupBang;
import frc.robot.commands.autoGroupBangBarrel;
import frc.robot.commands.autoGroupBangSix;
import frc.robot.commands.autoGroupBounce;
import frc.robot.commands.autoGroupCirclePractice;
import frc.robot.commands.autoGroupGyroTest;
import frc.robot.commands.autoGroupHopper;
import frc.robot.commands.autoGroupHopperTurn;
import frc.robot.commands.autoGroupShoot;
import frc.robot.commands.autoGroupShootAngle;
import frc.robot.commands.autoGroupShootAngleInvt;
import frc.robot.commands.autoGroupShootTrench;
import frc.robot.commands.autoGroupWeav;
import frc.robot.commands.autoGroupdecideballrun;
import frc.robot.commands.cmdBallSight;
import frc.robot.commands.cmdGroupHopperOuttake;
import frc.robot.commands.cmdLastResortAuto;
import frc.robot.subsystems.subChassis;
import frc.robot.subsystems.subClimb;
import frc.robot.subsystems.subControlPannel;
import frc.robot.subsystems.subGyro;
import frc.robot.subsystems.subHopper;
import frc.robot.subsystems.subHopperIntake;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subLauncher;
import frc.robot.subsystems.subLimelight;
import frc.robot.commands.autoGroupMove6Ft;
import frc.robot.commands.autoGroupRedBall; 
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
  public static subHopper subHopper;
  public static subHopperIntake subHopperIntake;
  

 
  
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
    subHopper = new subHopper();
    subHopperIntake = new subHopperIntake();


    

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
    SmartDashboard.putData("Auto mode", chooser);
    

    //chooser.addOption("Auto Drive By Gyro", new Auto_DriveByGyro(36, 72, 0));
    chooser.addOption("Auto Turn By Gyro", new Auto_TurnByGyro(90, 15));
    //chooser.setDefaultOption("Auto Drive Off Line", new Auto_DriveByGyro(40, 24, 0));
    //chooser.setDefaultOption("Auto Drive Off Line Rev", new Auto_DriveByGyro(-40, 24, 0));
    //chooser.setDefaultOption("Z TEST AUTO", new Auto_DriveByGyro(120, 20, 0));

    chooser.addOption("Auto Group Hopper", new autoGroupHopper());
    chooser.addOption("Auto Group Hopper Turn", new autoGroupHopperTurn());
    chooser.addOption("Auto Slalom Path", new autoGroupBang());
    chooser.addOption("Auto Barrel Run", new autoGroupBangBarrel());
    chooser.addOption("Auto Bounce Run", new autoGroupBounce());
    chooser.addOption("Initial D", new autoGroupCirclePractice());
    chooser.addOption("Gyro Test", new autoGroupGyroTest());
    chooser.addOption("Red B alls", new autoGroupRedBall());
    chooser.addOption("Red Balls  A Path", new autoGroupBallsRedFlip());
    chooser.addOption("auto decider", new autoGroupdecideballrun());

    chooser.addOption("Auto Group Stright", new autoGroupShootAngle());
    chooser.addOption("BANG BANG", new autoGroupBangSix());
    chooser.addOption("Auto Group Shoot", new autoGroupShoot());
    chooser.addOption("Auto Group Delay Shoot", new autoGroupShootAngleInvt()); 
    chooser.addOption("Auto hoppertest", new cmdGroupHopperOuttake());
    chooser.setDefaultOption("Trench Auto", new autoGroupShootTrench());
    chooser.addOption("Weaver",new autoGroupWeav());
    chooser.addOption("6 Feet", new autoGroupMove6Ft());
    chooser.addOption("Ball Sight", new cmdBallSight());
    //chooser.setDefaultOption("Drive By Gyro", autonomousCommand);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
  }

  /**
   * This function is called when the disabled button i
   * s hit. You can use it to
   * reset subsystems before shutting down.
   */
  @Override
  public void disabledInit() {

  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    UpdateSmartDashboard();
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
    //UpdateSmartDashboard();
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
    UpdateSmartDashboard();
    subLauncher.lv = SmartDashboard.getNumber("LV", 2850);
  }

  public void UpdateSmartDashboard () {
    //System.err.println ("Updating smartDashboard");
    
    SmartDashboard.putNumber("Chassis/OutputRight", subChassis.rightDrive.get());
    SmartDashboard.putNumber("Chassis/VelocityRight", subChassis.rightDrive.getVelocity());
    SmartDashboard.putNumber("Chassis/OutputLeft", subChassis.leftDrive.get());
    SmartDashboard.putNumber("Chassis/VelocityLeft", subChassis.leftDrive.getVelocity());
    SmartDashboard.putNumber("launcher vel", subLauncher.flyWheelMotor.getVelocity());
    SmartDashboard.putNumber("Gyro", Robot.subGyro.getNavxAngleRaw());
    SmartDashboard.putNumber("Power Count", Settings.powerCount);
    //SmartDashboard.putNumber("Launcher Velocity", subLauncher.launcherMaxRPM);
    //SmartDashboard.putNumber("LV", 2850);

    //SmartDashboard.putNumber("Chassis/OutputRight", 1.0);
    //SmartDashboard.putNumber("Chassis/VelocityRight", 2.0);
    //SmartDashboard.putNumber("Chassis/OutputLeft", 3.0);
    //SmartDashboard.putNumber("Chassis/VelocityLeft", 4.0);



  }

}