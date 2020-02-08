/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Settings;
import frc.robot.Settings;
import frc.robot.hardware.wlSpark;

/**
 * Add your docs here.
 */
public class subHopperIntake extends Subsystem {
  public static wlSpark hopperIntakeMotor;

public subHopperIntake(){
  hopperIntakeMotor = new wlSpark(Settings.CANID_subHopperIntakeMotor, MotorType.kBrushless);

}
public void hopperIntakeIn(){
  hopperIntakeMotor.set(.5);
}
public void hopperOuttake(){
  hopperIntakeMotor.set(-.5);
}
public void hopperStopper(){
  hopperIntakeMotor.set(0);
}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
