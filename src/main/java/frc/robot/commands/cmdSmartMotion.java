/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.subChassis;

public class cmdSmartMotion extends CommandBase {
  

  public cmdSmartMotion() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     // read PID coefficients from SmartDashboard
     double p = SmartDashboard.getNumber("P Gain", 0);
     double i = SmartDashboard.getNumber("I Gain", 0);
     double d = SmartDashboard.getNumber("D Gain", 0);
     double iz = SmartDashboard.getNumber("I Zone", 0);
     double ff = SmartDashboard.getNumber("Feed Forward", 0);
     double max = SmartDashboard.getNumber("Max Output", 0);
     double min = SmartDashboard.getNumber("Min Output", 0);
     double maxV = SmartDashboard.getNumber("Max Velocity", 0);
     double minV = SmartDashboard.getNumber("Min Velocity", 0);
     double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
     double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);
 
     // if PID coefficients on SmartDashboard have changed, write new values to controller
     if((p != subChassis.kP)) { subChassis.m_pidController.setP(p); subChassis.kP = p; }
     if((i != subChassis.kI)) { subChassis.m_pidController.setI(i); subChassis.kI = i; }
     if((d != subChassis.kD)) { subChassis.m_pidController.setD(d); subChassis.kD = d; }
     if((iz != subChassis.kIz)) { subChassis.m_pidController.setIZone(iz); subChassis.kIz = iz; }
     if((ff != subChassis.kFF)) { subChassis.m_pidController.setFF(ff); subChassis.kFF = ff; }
     if((max != subChassis.kMaxOutput) || (min != subChassis.kMinOutput)) { 
      subChassis.m_pidController.setOutputRange(min, max); 
       subChassis.kMinOutput = min;subChassis.kMaxOutput = max; 
     }
     if((maxV != subChassis.maxVel)) { subChassis.m_pidController.setSmartMotionMaxVelocity(maxV,0); subChassis.maxVel = maxV; }
     if((minV != subChassis.minVel)) { subChassis.m_pidController.setSmartMotionMinOutputVelocity(minV,0); subChassis.minVel = minV; }
     if((maxA != subChassis.maxAcc)) { subChassis.m_pidController.setSmartMotionMaxAccel(maxA,0); subChassis.maxAcc = maxA; }
     if((allE != subChassis.allowedErr)) { subChassis.m_pidController.setSmartMotionAllowedClosedLoopError(allE,0); subChassis.allowedErr = allE; }
 
     double setPoint, processVariable;
     boolean mode = SmartDashboard.getBoolean("Mode", false);
     if(mode) {
       setPoint = SmartDashboard.getNumber("Set Velocity", 0);
       subChassis.m_pidController.setReference(setPoint, ControlType.kVelocity);
       processVariable = subChassis.leftEncoder.getVelocity();
     } else {
       setPoint = SmartDashboard.getNumber("Set Position", 0);
       /**
        * As with other PID modes, Smart Motion is set by calling the
        * setReference method on an existing pid object and setting
        * the control type to kSmartMotion
        */
        subChassis.m_pidController.setReference(setPoint, ControlType.kSmartMotion);
       processVariable = subChassis.leftEncoder.getPosition();
     }
     
     SmartDashboard.putNumber("SetPoint", setPoint);
     SmartDashboard.putNumber("Process Variable", processVariable);
     SmartDashboard.putNumber("Output", subChassis.leftDrive.getAppliedOutput());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
