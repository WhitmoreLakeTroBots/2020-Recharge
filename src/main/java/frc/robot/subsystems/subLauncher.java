// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Settings;
import frc.robot.hardware.wlSpark;


// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class subLauncher extends Subsystem {
public static wlSpark flyWheelMotor;
public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVEL, maxACC, lv;
public CANPIDController flywheelPIDC;
public double allowedErr = 0;

//public static final double launcherMaxRPM = lv;//2850//3800 for basketball
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public subLauncher() {
       flyWheelMotor = new wlSpark(Settings.CANID_subLauncherFlyWheel, MotorType.kBrushless);
        flywheelPIDC = flyWheelMotor.getPIDController();
        flyWheelMotor.restoreFactoryDefaults();
    kP = 7e-5;
    kI = 4e-7;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1;
    kMinOutput = -1;
    maxRPM = 5000;

    maxVel = 5000;
    maxACC = 3000;

    flywheelPIDC.setP(kP);
    flywheelPIDC.setI(kI);
    flywheelPIDC.setD(kD);
    flywheelPIDC.setIZone(kIz);
    flywheelPIDC.setFF(kFF);
    flywheelPIDC.setOutputRange(kMinOutput, kMaxOutput);

  /*  if(Robot.subLimelight.getTY() > 1.0) {
        lv = 2850;
    }
    else if(Robot.subLimelight.getTY() < 2.0 ){
        lv = 3600;
    }
    else{
        lv = 2850;
    }
    
    */
    

    }
    @Override
    public void periodic() {

        //lv = SmartDashboard.getNumber("LV",2850);
    

    }
    public void runFlyWheel(){
        flywheelPIDC.setReference(2750, ControlType.kVelocity);
        System.err.println("RUNNING");
        //flyWheelMotor.set(.75);
    }
    public void runFlyWheelFaster(){
        flywheelPIDC.setReference(lv + 800, ControlType.kVelocity);
        System.err.println("RUNNING");
        //flyWheelMotor.set(.75);
    }
    public void stopWheel(){
        flyWheelMotor.set(0);
    }

    
    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

   

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

