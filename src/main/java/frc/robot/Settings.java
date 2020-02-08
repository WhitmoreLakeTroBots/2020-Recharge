/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Settings {

  // CANIDs
  public static final int CANID_subChassisRightMaster = 2;
  public static final int CANID_subChassisRightFollower = 3;
  public static final int CANID_subChassisLeftMaster = 7;
  public static final int CANID_subChassisLeftFollower = 4;
  public static final int CANID_subLauncherFlyWheel = 8;
  public static final int CANID_subIntakeIntake = 9;
  public static final int CANID_subHopperMotor = 10;
  public static final int CANID_subHopperIntakeMotor = 11;
  public static final int CANID_subClimbMotor = 12;
  public static final int CANID_subClimbMotorInv = 14;

  public static final int REV_NEO_CurrentLimitStalledAmps = 27;
  public static final int REV_NEO_CurrentLimitFreeAmps = 27;

  // chassis constants
  public static final double chassisDriveStraightGyroKp = 0.05;
  public static final double Chassis_powerLeftScaler = 1.0;
  public static final double Chassis_powerRightScaler = 1.0;
  public static final double chassisMaxInchesPerSec = 212;

  //chassis hopper
  public static final double hopperStartPos = 0;
  public static final double hopperIntakePos = -9.0961;
  public static final double hopperLauncherPos = -34.4528;
  public static final double hopperDumpPos = -73.547931;
  public static final double hopperStrightPos = -60.595611;
  public static final double hopperMotorSpeed = -0.3;




  // Profile Settings
  public static final String profileTestLogName = "logs\\motionProfileTestResults";
  public static final String profileLogName = "//media//sda1//motionProfile";
  public static final double profileAdditionLoopNumber = 50;
  public static final String profileLogFileExtension = ".txt";
  public static final double profileDriveAccelration = 75; // inches/sec/sec
  public static final double profileTurnAcceleration = 50; // inches/sec/sec
  public static final double profileAnglarAccelration = 10;
  public static final double profileInitVelocity = 0.0;
  public static final double profileDefaultTurnVelocity = 50;  //inches/sec
  public static final double profileMovementThreshold = 0.75;
  public static final double profileEndTimeScalar = 1.3;
  public static final double profileEndTol = .25; 

}
