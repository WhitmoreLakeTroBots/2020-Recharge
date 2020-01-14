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

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CommonLogic;
import frc.robot.Settings;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class subChassis extends Subsystem {

  public static CANSparkMax leftDrive;
  public static CANSparkMax rightDrive;
  public static CANEncoder leftEncoder;
  public static CANEncoder rightEncoder;
  public static CANPIDController leftPidC = null;
  public static CANPIDController rightPidC = null;

  public static final double joyDriveDeadband = 0.05;
  public static final double driveStraightGyroKp = 0.05;
  public static final double wheelDiameter = 6.0;
  public static final double gearBoxRatio = 8.45;
  private Joystick leftStick;

  public subChassis() {
    
    leftDrive = new CANSparkMax(Settings.CANID_subChassisLeftMaster, MotorType.kBrushless);
    rightDrive = new CANSparkMax(Settings.CANID_subChassisRightMaster, MotorType.kBrushless);
    leftStick = new Joystick(0);
    leftDrive.restoreFactoryDefaults();
    rightDrive.restoreFactoryDefaults();
    
    leftDrive.setInverted(true);
    
    leftDrive.setIdleMode(IdleMode.kBrake);
    rightDrive.setIdleMode(IdleMode.kBrake);

    leftEncoder = leftDrive.getEncoder();
    rightEncoder = rightDrive.getEncoder();

    rightPidC = rightDrive.getPIDController();
    leftPidC = leftDrive.getPIDController();

    configureSmartMotion();
    configureTeleOptMotion();
    
    // burn in the values so they stay during a brown out.
    rightDrive.burnFlash();
    leftDrive.burnFlash();

  }

  public static class smartMotionKs {
    public final static int slot = 0;
    public final static double kP = 5e-5;
    public final static double kI = 1e-6;
    public final static double kD = 0;
    public final static double kIz = 0;
    public final static double kFF = 0.000156;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
    public final static int maxRPM = 5700;
    public final static int minRPM = 0;
    // allow .5 seconds to reach max RPM
    public final static int maxAcc = maxRPM * 2;
    public final static int allowedErr = maxRPM /10;

  }

  public static class teleOpMotionKs {
    public final static int slot = 1;
    public final static double kP = 5e-4;
    public final static double kI = 1e-5;
    public final static double kD = 0;
    public final static double kIz = 0;
    public final static double kFF = 0.000156;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
    public final static int maxRPM = 5700;
    public final static int minRPM = 0;
    // allow .5 seconds to reach max RPM
    public final static int maxAcc = maxRPM * 2;
    public final static int allowedErr = maxRPM /10;

  }

  public void Drive(Joystick stick) {

    double joyX = CommonLogic.joyDeadBand(stick.getX(), joyDriveDeadband);
    double joyY = CommonLogic.joyDeadBand(stick.getY(), joyDriveDeadband);
    leftPidC.setReference((joyY + joyX) * teleOpMotionKs.maxRPM, ControlType.kVelocity, teleOpMotionKs.slot);
    rightPidC.setReference((joyY - joyX) * teleOpMotionKs.maxRPM, ControlType.kVelocity, teleOpMotionKs.slot);

  }

  private void configureSmartMotion() {
    // configures smart motion for the drive train sparks

    leftPidC.setP(smartMotionKs.kP, smartMotionKs.slot);
    rightPidC.setP(smartMotionKs.kP, smartMotionKs.slot);

    leftPidC.setD(smartMotionKs.kD, smartMotionKs.slot);
    rightPidC.setD(smartMotionKs.kD, smartMotionKs.slot);

    leftPidC.setI(smartMotionKs.kI, smartMotionKs.slot);
    rightPidC.setI(smartMotionKs.kI, smartMotionKs.slot);

    leftPidC.setIZone(smartMotionKs.kIz, smartMotionKs.slot);
    leftPidC.setIZone(smartMotionKs.kIz, smartMotionKs.slot);

    leftPidC.setFF(smartMotionKs.kFF, smartMotionKs.slot);
    leftPidC.setFF(smartMotionKs.kFF, smartMotionKs.slot);

    leftPidC.setOutputRange(smartMotionKs.kMinOutput, smartMotionKs.kMaxOutput, smartMotionKs.slot);
    leftPidC.setOutputRange(smartMotionKs.kMinOutput, smartMotionKs.kMaxOutput, smartMotionKs.slot);

  }

  private void configureTeleOptMotion() {
    // configures TeleOp motion for the drive train sparks
    leftPidC.setP(teleOpMotionKs.kP, teleOpMotionKs.slot);
    rightPidC.setP(teleOpMotionKs.kP, teleOpMotionKs.slot);

    leftPidC.setD(teleOpMotionKs.kD, teleOpMotionKs.slot);
    rightPidC.setD(teleOpMotionKs.kD, teleOpMotionKs.slot);

    leftPidC.setI(teleOpMotionKs.kI, teleOpMotionKs.slot);
    rightPidC.setI(teleOpMotionKs.kI, teleOpMotionKs.slot);

    leftPidC.setIZone(teleOpMotionKs.kIz, teleOpMotionKs.slot);
    rightPidC.setIZone(teleOpMotionKs.kIz, teleOpMotionKs.slot);

    leftPidC.setFF(teleOpMotionKs.kFF, teleOpMotionKs.slot);
    rightPidC.setFF(teleOpMotionKs.kFF, teleOpMotionKs.slot);

    leftPidC.setOutputRange(teleOpMotionKs.kMinOutput, teleOpMotionKs.kMaxOutput, teleOpMotionKs.slot);
    rightPidC.setOutputRange(teleOpMotionKs.kMinOutput, teleOpMotionKs.kMaxOutput, teleOpMotionKs.slot);

    leftPidC.setSmartMotionMaxVelocity(teleOpMotionKs.maxRPM, teleOpMotionKs.slot);
    rightPidC.setSmartMotionMaxVelocity(teleOpMotionKs.maxRPM, teleOpMotionKs.slot);

    leftPidC.setSmartMotionMinOutputVelocity(teleOpMotionKs.minRPM, teleOpMotionKs.slot);
    rightPidC.setSmartMotionMinOutputVelocity(teleOpMotionKs.minRPM, teleOpMotionKs.slot);

    leftPidC.setSmartMotionMaxAccel(teleOpMotionKs.maxAcc, teleOpMotionKs.slot);
    rightPidC.setSmartMotionMaxAccel(teleOpMotionKs.maxAcc, teleOpMotionKs.slot);
    
    leftPidC.setSmartMotionAllowedClosedLoopError(teleOpMotionKs.allowedErr, teleOpMotionKs.slot);
    rightPidC.setSmartMotionAllowedClosedLoopError(teleOpMotionKs.allowedErr, teleOpMotionKs.slot);

  }

  public void smartMotion_activate(double inches) {
    // sets the motion type to SmartMotion
    rightEncoder.setPosition(0);
    double revs = inches2MotorRevs(inches);
    rightPidC.setReference(revs, ControlType.kSmartMotion, smartMotionKs.slot);

    leftEncoder.setPosition(0);
    leftPidC.setReference(revs, ControlType.kSmartMotion, smartMotionKs.slot);

  }

  public double inches2MotorRevs (double inches) {
    // convert inches to motor Revs
    return (inches / wheelDiameter / Math.PI * gearBoxRatio);
    // 72 / 6 / Math.P * 8.45 = 32.2766224
  }

  public void smartMotion_steerStraight(){

    // sets max and min motion velocity to steer while driving to position
    /* leftPidC.setSmartMotionMaxVelocity(leftVel * 1.01, smartMotionKs.slot);
    rightPidC.setSmartMotionMaxVelocity(rightVel * 1.01, smartMotionKs.slot);

    leftPidC.setSmartMotionMinOutputVelocity(leftVel * .99, smartMotionKs.slot);
    rightPidC.setSmartMotionMinOutputVelocity(rightVel * .99, smartMotionKs.slot);
    */
  }

  public boolean smartMotion_isDone() {

    double leftPos = leftEncoder.getPosition();
    double rightPos = rightEncoder.getPosition();
    double average = (Math.abs(leftPos) + Math.abs(rightPos)) /2;
    
    if (average > .99) {

    }
    return false;
  }


  public void activateTeleOpMotion() {
    
    // puts the sparks into a drive mode where we can set Velocity percentages
    leftPidC.setReference(0, ControlType.kVelocity, teleOpMotionKs.slot);
    rightPidC.setReference(0, ControlType.kVelocity, teleOpMotionKs.slot);
  }

  

  @Override
  public void initDefaultCommand() {

  }

  @Override
  public void periodic() {
    Drive(leftStick);
  }

}