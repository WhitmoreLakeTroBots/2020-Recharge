/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.subLimelight;
import frc.robot.subsystems.subLimelight.LED_MODE;

/**
 * Add your docs here.
 */
public class cmdLimeAdjust extends InstantCommand {
  
  private double angleError;
  private double angleDividend = 0.1;
  

    public cmdLimeAdjust() {
        requires(Robot.subChassis);
    }

    protected void initialize() {
        Robot.subLimelight.setLEDMode(LED_MODE.OFF);        
        Robot.subLimelight.setCamMode(subLimelight.CAM_MODE.VISION_PROCESSING);
        Robot.subLimelight.setPipeline(0);
    }

    protected void execute() {

        angleError = Robot.subLimelight.getTX() - 8;
        SmartDashboard.putNumber("ANGLE ERROR", angleError);
        System.err.println("ANGLE ERROR " + angleError);
        double turn = angleError * angleDividend;
        double yVal = Robot.oi.joyDrive().getY();
      System.err.println("TURNING: " + turn);
        SmartDashboard.putNumber("TURN", turn);

        new Auto_TurnByGyro(turn,24);


    }

    @Override
  protected void interrupted() {
    Robot.subChassis.Drive(0, 0);;
  }

  

}

