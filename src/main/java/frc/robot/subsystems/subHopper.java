/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Set;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CommonLogic;
import frc.robot.Settings;
import frc.robot.hardware.wlSpark;

/**
 * Add your docs here.
 */
public class subHopper extends Subsystem {
 public static wlSpark hopperSpark;



public subHopper(){
  hopperSpark = new wlSpark(Settings.CANID_subHopperMotor, MotorType.kBrushless);
  hopperSpark.resetEncoder();

}
public double encodercount(){
  return hopperSpark.getPosition();
}

public void hopperStart(){
  if(hopperSpark.getPosition() > Settings.hopperStartPos){
    hopperSpark.set(Settings.hopperMotorSpeed);
  }
  else if(hopperSpark .getPosition() < Settings.hopperStartPos){
    hopperSpark.set(-Settings.hopperMotorSpeed);
  }
  else if(CommonLogic.isInRange(hopperSpark.getPosition(), Settings.hopperStartPos, 2)){
    hopperSpark.set(0);
  }
}

public void hopperIntake(){
  if(hopperSpark.getPosition() > Settings.hopperIntakePos){
    hopperSpark.set(Settings.hopperMotorSpeed);
  }
  else if(hopperSpark .getPosition() < Settings.hopperIntakePos){
    hopperSpark.set(-Settings.hopperMotorSpeed);
  }
  else if(CommonLogic.isInRange(hopperSpark.getPosition(), Settings.hopperIntakePos, 2)){
    hopperSpark.set(0);
  }
}
public void hopperLauncher(){
  if(hopperSpark.getPosition() > Settings.hopperLauncherPos){
    hopperSpark.set(Settings.hopperMotorSpeed);
  }
  else if(hopperSpark .getPosition() < Settings.hopperLauncherPos){
    hopperSpark.set(-Settings.hopperMotorSpeed);
  }
  else if(CommonLogic.isInRange(hopperSpark.getPosition(), Settings.hopperLauncherPos, 2)){
    hopperSpark.set(0);
  }
}
public void hopperDump(){
  if(hopperSpark.getPosition() > Settings.hopperDumpPos){
    hopperSpark.set(Settings.hopperMotorSpeed);
  }
  else if(hopperSpark .getPosition() < Settings.hopperDumpPos){
    hopperSpark.set(-Settings.hopperMotorSpeed);
  }
  else if(CommonLogic.isInRange(hopperSpark.getPosition(), Settings.hopperDumpPos, 2)){
    hopperSpark.set(0);
  }
}
public void hopperStright(){
  if(hopperSpark.getPosition() > Settings.hopperStrightPos){
    hopperSpark.set(Settings.hopperMotorSpeed);
  }
  else if(hopperSpark .getPosition() < Settings.hopperStrightPos){
    hopperSpark.set(-Settings.hopperMotorSpeed);
  }
  else if(CommonLogic.isInRange(hopperSpark.getPosition(), Settings.hopperStrightPos, 2)){
    hopperSpark.set(0);
  }
}
public void hopperStop(){
  hopperSpark.set(0);
}

  @Override
  public void initDefaultCommand() {
    
  }
}
