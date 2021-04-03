// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.Settings;

public class autoGroupdecideballrun extends CommandGroup {
  double speedSlow = 0.3;
  double speedMed = 0.6;
  double speedFast = 0.8;
  double insidek = -.13;
  double outsidek = .5;
  /** Add your docs here. */
  public autoGroupdecideballrun() {
    addSequential(new cmdSetLowLimelightProfile());;
    if(Robot.subLimelight.getLTV2() != 0.00 ){
        addSequential(new autoGroupRedBall());
      }else{
        addSequential(new autoGroupBlueBallsB());
      }
 
  }
}
