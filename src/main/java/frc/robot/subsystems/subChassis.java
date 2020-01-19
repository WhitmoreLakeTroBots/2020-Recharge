package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CommonLogic;
import frc.robot.Settings;
import frc.robot.commands.*;
import static frc.robot.PidConstants.*;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class subChassis extends Subsystem {

  private static CANSparkMax leftDrive;
  private static CANSparkMax rightDrive;
  private static CANEncoder leftEncoder;
  private static CANEncoder rightEncoder;
  private static CANPIDController leftPidC = null;
  private static CANPIDController rightPidC = null;

  public static final double joyDriveDeadband = 0.05;
  public static final double driveStraightGyroKp = 0.05;
  public static final double wheelDiameter = 6.0;
  public static final double gearBoxRatio = 8.45;
  

  public subChassis() {
    
    leftDrive = new CANSparkMax(Settings.CANID_subChassisLeftMaster, MotorType.kBrushless);
    rightDrive = new CANSparkMax(Settings.CANID_subChassisRightMaster, MotorType.kBrushless);
	// reset factory defaults and make them persist power cycles
    leftDrive.restoreFactoryDefaults(true);
    rightDrive.restoreFactoryDefaults(true);
    
    
    leftDrive.setInverted(true);
    
    leftDrive.setIdleMode(IdleMode.kBrake);
    rightDrive.setIdleMode(IdleMode.kBrake);

    leftEncoder = leftDrive.getEncoder();
    rightEncoder = rightDrive.getEncoder();

    rightPidC = rightDrive.getPIDController();
    leftPidC = leftDrive.getPIDController();

    configureSmartMotion(rightPidC);
    configureSmartMotion(leftPidC);

    configureTeleOptMotion(rightPidC);
    configureTeleOptMotion(leftPidC);

    // burn in the values so they stay during a brown out.
    rightDrive.burnFlash();
    leftDrive.burnFlash();

  }

  public void Drive(Joystick stick) {

    double joyX = CommonLogic.joyDeadBand(stick.getX(), joyDriveDeadband);
    double joyY = CommonLogic.joyDeadBand(stick.getY(), joyDriveDeadband);

    double rightDriveValue = joyY + joyX;
    double leftDriveValue = joyY -joyX;

    setVelocity_RightDrive(rightDriveValue * Chassis_teleOpMotionKs.maxRPM);
    setVelocity_LeftDrive(leftDriveValue * Chassis_teleOpMotionKs.maxRPM); 

    // kill the iAccumulator if we are setting an entire side of the
    // drivetrain to zero
    if (CommonLogic.joyDeadBand(rightDriveValue, joyDriveDeadband) == 0.0){
      rightPidC.setIAccum(0.0);
    }

    if (CommonLogic.joyDeadBand(leftDriveValue, joyDriveDeadband) == 0.0){
      leftPidC.setIAccum(0.0);
    }

  }

  private void configureSmartMotion(CANPIDController pidControler) {
    // configures smart motion for the drive train sparks
    CANError err = CANError.kOk;

    pidControler.setP(Chassis_smartMotionKs.kP);
    pidControler.setI(Chassis_smartMotionKs.kI);
    pidControler.setD(Chassis_smartMotionKs.kD);
    pidControler.setIZone(Chassis_smartMotionKs.kIz);
    pidControler.setFF(Chassis_smartMotionKs.kFF);
    pidControler.setOutputRange(Chassis_smartMotionKs.kMinOutput, Chassis_smartMotionKs.kMaxOutput);
    pidControler.setSmartMotionMaxVelocity(Chassis_smartMotionKs.maxRPM, Chassis_smartMotionKs.slot);
    pidControler.setSmartMotionMinOutputVelocity(Chassis_smartMotionKs.maxRPM,Chassis_smartMotionKs.slot);  
    pidControler.setSmartMotionMaxAccel(Chassis_smartMotionKs.maxAcc,Chassis_smartMotionKs.slot);
    pidControler.setSmartMotionAllowedClosedLoopError(0, Chassis_smartMotionKs.slot);
  

    //https://github.com/HHS-Team670/2019-Robot/blob/dev/2019-Robot/src/main/java/frc/team670/robot/subsystems/DriveBase.java
    
    
  }
  /****************************************************************************
    Velocity Settings & Methods
  ****************************************************************************/
  private void configureTeleOptMotion(CANPIDController pidControler) {
    // configures TeleOp motion for the drive train sparks
    pidControler.setP(Chassis_teleOpMotionKs.kP, Chassis_teleOpMotionKs.slot);
    pidControler.setD(Chassis_teleOpMotionKs.kD, Chassis_teleOpMotionKs.slot);
    pidControler.setI(Chassis_teleOpMotionKs.kI, Chassis_teleOpMotionKs.slot);
    pidControler.setIZone(Chassis_teleOpMotionKs.kIz, Chassis_teleOpMotionKs.slot);
    pidControler.setFF(Chassis_teleOpMotionKs.kFF, Chassis_teleOpMotionKs.slot);
    pidControler.setOutputRange(Chassis_teleOpMotionKs.kMinOutput, Chassis_teleOpMotionKs.kMaxOutput, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionMaxVelocity(Chassis_teleOpMotionKs.maxRPM, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionMinOutputVelocity(Chassis_teleOpMotionKs.minRPM, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionMaxAccel(Chassis_teleOpMotionKs.maxAcc, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionAllowedClosedLoopError(Chassis_teleOpMotionKs.allowedErr, Chassis_teleOpMotionKs.slot);
    
  }

  public void setVelocity_LeftDrive (double velRPM) {
    double RPM = CommonLogic.CapMotorPower(velRPM, -Chassis_teleOpMotionKs.maxRPM, Chassis_teleOpMotionKs.maxRPM);
    leftPidC.setReference(RPM, ControlType.kVelocity, Chassis_teleOpMotionKs.slot);
    
}

  public void setVelocity_RightDrive (double velRPM) {
    double RPM = CommonLogic.CapMotorPower(velRPM, -Chassis_teleOpMotionKs.maxRPM, Chassis_teleOpMotionKs.maxRPM);
    rightPidC.setReference(RPM, ControlType.kVelocity, Chassis_teleOpMotionKs.slot);
    
  }

  /****************************************************************************
    Position Settings & Methods
  ****************************************************************************/
  
  
  public void resetEncoder_LeftDrive () {
    leftEncoder.setPosition(0);
  }
  
  public void resetEncoder_RightDrive () {
    rightEncoder.setPosition(0);
  }

  public void setSmartPosition_LeftDrive(double ref_Revs, double RPM){
    // sets left motor to run to position
    configureSmartMotion(leftPidC);
    leftPidC.setReference(ref_Revs, ControlType.kSmartMotion);
    leftPidC.setSmartMotionMaxVelocity(RPM, Chassis_smartMotionKs.slot);
  }

  public  void setSmartPosition_RightDrive (double ref_Revs, double RPM) {
    // sets right motor to run to position
    
    configureSmartMotion(rightPidC);
    rightPidC.setReference(ref_Revs, ControlType.kSmartMotion);
    rightPidC.setSmartMotionMaxVelocity(RPM, Chassis_smartMotionKs.slot);
     
  }

  public void smartPosition_steerStraight(double leftRPM, double rightRPM, double tol){
    // steer the robot by changing the smartMotion velocity of the left and right sides of the chassis
    double loTol = 1 - tol;
    double hiTol = 1 + tol;

    // sets max and min motion velocity to steer while driving to position
    leftPidC.setSmartMotionMaxVelocity(leftRPM * hiTol, Chassis_smartMotionKs.slot);
    rightPidC.setSmartMotionMaxVelocity(rightRPM * hiTol, Chassis_smartMotionKs.slot);
    
    //leftPidC.setSmartMotionMinOutputVelocity(leftRPM * loTol, Chassis_smartMotionKs.slot);
    //rightPidC.setSmartMotionMinOutputVelocity(rightRPM * loTol, Chassis_smartMotionKs.slot);
    
  }

  public boolean smartPosition_isDoneLeft (double desired_Revs, double Tol) {
    
    double currPos = leftEncoder.getPosition();
    return CommonLogic.isInRange(currPos, desired_Revs, Tol);
  }
  
  public boolean smartPosition_isDoneRight (double desired_Revs, double Tol) {
    
    double currPos = rightEncoder.getPosition();
    return CommonLogic.isInRange(currPos, desired_Revs, Tol);
  }

  public boolean smartPosition_LR_isDone(double desired_Revs, double Tol) {

    return (smartPosition_isDoneRight(desired_Revs, Tol) && 
            smartPosition_isDoneLeft(desired_Revs, Tol) );
   
  }

  public double inches2MotorRevs (double inches) {
    // convert inches to motor Revs
    return (inches / wheelDiameter / Math.PI * gearBoxRatio);
    // 72 / 6 / Math.P * 8.45 = 32.2766224
  }

  public double inches_sec2RPM (double inches_sec) {
    // converts inches/sec to Revs/minute
    return inches2MotorRevs(inches_sec) * 60;
  }

  public void stop () {
    
    leftDrive.set(0);
    rightDrive.set (0);
    leftPidC.setIAccum(0);
    rightPidC.setIAccum(0);

  }

  @Override
  public void initDefaultCommand() {
		setDefaultCommand(new cmdTeleOp());

  }


  @Override
  public void periodic() {
    //Drive(leftStick);
  }

}