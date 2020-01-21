package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CommonLogic;
import frc.robot.PidConstants.Chassis_smartPositionKs;
import frc.robot.PidConstants.Chassis_teleOpMotionKs;
import frc.robot.Settings;
import frc.robot.commands.cmdTeleDrive;
import frc.robot.hardware.wlSpark;

/**
 *
 */
public class subChassis extends Subsystem {

  private static wlSpark leftDrive;
  private static wlSpark rightDrive;

  public static final double joyDriveDeadband = 0.05;
  public static final double driveStraightGyroKp = 0.05;
  public static final double wheelDiameter = 6.0;
  public static final double gearBoxRatio = 8.45;

  public subChassis() {

    leftDrive = new wlSpark(Settings.CANID_subChassisLeftMaster, MotorType.kBrushless);
    rightDrive = new wlSpark(Settings.CANID_subChassisRightMaster, MotorType.kBrushless);
    // reset factory defaults and make them persist power cycles
    leftDrive.restoreFactoryDefaults(true);
    rightDrive.restoreFactoryDefaults(true);

    leftDrive.setIdleMode(IdleMode.kBrake);
    rightDrive.setIdleMode(IdleMode.kBrake);
    leftDrive.setInverted(true);
    
    configureSmartPosition(leftDrive.getPIDController());
    configureSmartPosition(rightDrive.getPIDController());

    configureTeleOptMotion(leftDrive.getPIDController());
    configureTeleOptMotion(rightDrive.getPIDController());

    // burn in the values so they stay during a brown out.
    rightDrive.burnFlash();
    leftDrive.burnFlash();

  }

  public void Drive(Joystick stick) {

    double joyX = CommonLogic.joyDeadBand(stick.getX(), joyDriveDeadband);
    double joyY = CommonLogic.joyDeadBand(stick.getY(), joyDriveDeadband);

    double rightDriveValue = joyY + joyX;
    double leftDriveValue = joyY - joyX;

    setVelocity_RightDrive(rightDriveValue * Chassis_teleOpMotionKs.maxRPM);
    setVelocity_LeftDrive(leftDriveValue * Chassis_teleOpMotionKs.maxRPM);

    // kill the iAccumulator if we are setting an entire side of the
    // drivetrain to zero
    if (CommonLogic.joyDeadBand(rightDriveValue, joyDriveDeadband) == 0.0) {
      rightDrive.getPIDController().setIAccum(0.0);
    }

    if (CommonLogic.joyDeadBand(leftDriveValue, joyDriveDeadband) == 0.0) {
      leftDrive.getPIDController().setIAccum(0.0);
    }

  }

  /****************************************************************************
   * Velocity Settings & Methods
   ****************************************************************************/
  private void configureTeleOptMotion(CANPIDController pidControler) {
    // configures TeleOp motion for the drive train sparks
    pidControler.setP(Chassis_teleOpMotionKs.kP, Chassis_teleOpMotionKs.slot);
    pidControler.setD(Chassis_teleOpMotionKs.kD, Chassis_teleOpMotionKs.slot);
    pidControler.setI(Chassis_teleOpMotionKs.kI, Chassis_teleOpMotionKs.slot);
    pidControler.setIZone(Chassis_teleOpMotionKs.kIz, Chassis_teleOpMotionKs.slot);
    pidControler.setFF(Chassis_teleOpMotionKs.kFF, Chassis_teleOpMotionKs.slot);
    pidControler.setOutputRange(Chassis_teleOpMotionKs.kMinOutput, Chassis_teleOpMotionKs.kMaxOutput,
        Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionMaxVelocity(Chassis_teleOpMotionKs.maxRPM, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionMinOutputVelocity(Chassis_teleOpMotionKs.minRPM, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionMaxAccel(Chassis_teleOpMotionKs.maxAcc, Chassis_teleOpMotionKs.slot);
    pidControler.setSmartMotionAllowedClosedLoopError(Chassis_teleOpMotionKs.allowedErr, Chassis_teleOpMotionKs.slot);

  }

  public void setVelocity_LeftDrive(double velRPM) {
    double RPM = CommonLogic.CapMotorPower(velRPM, -Chassis_teleOpMotionKs.maxRPM, Chassis_teleOpMotionKs.maxRPM);
    leftDrive.setReferenceVelocity(RPM, Chassis_teleOpMotionKs.ctrlType, Chassis_teleOpMotionKs.slot);

  }

  public void setVelocity_RightDrive(double velRPM) {
    double RPM = CommonLogic.CapMotorPower(velRPM, -Chassis_teleOpMotionKs.maxRPM, Chassis_teleOpMotionKs.maxRPM);
    rightDrive.setReferenceVelocity(RPM, Chassis_teleOpMotionKs.ctrlType, Chassis_teleOpMotionKs.slot);

  }

  /****************************************************************************
   * Position Settings & Methods
   ****************************************************************************/
  private void configureSmartPosition(CANPIDController pidController) {
    // configures smart motion for the drive train sparks
    pidController.setP(Chassis_smartPositionKs.kP);
    pidController.setI(Chassis_smartPositionKs.kI);
    pidController.setD(Chassis_smartPositionKs.kD);
    pidController.setIZone(Chassis_smartPositionKs.kIz);
    pidController.setFF(Chassis_smartPositionKs.kFF);
    pidController.setOutputRange(Chassis_smartPositionKs.kMinOutput, Chassis_smartPositionKs.kMaxOutput);
    pidController.setSmartMotionMaxVelocity(Chassis_smartPositionKs.maxRPM, Chassis_smartPositionKs.slot);
    pidController.setSmartMotionMinOutputVelocity(Chassis_smartPositionKs.maxRPM, Chassis_smartPositionKs.slot);
    pidController.setSmartMotionMaxAccel(Chassis_smartPositionKs.maxAcc, Chassis_smartPositionKs.slot);
    pidController.setSmartMotionAllowedClosedLoopError(0, Chassis_smartPositionKs.slot);
    // https://github.com/HHS-Team670/2019-Robot/blob/dev/2019-Robot/src/main/java/frc/team670/robot/subsystems/DriveBase.java

  }

  public double getEncoderPos_LR() {
    return (getEncoderPosRight());
  }

  public double getEncoderPosLeft() {
    return leftDrive.getPosition(); 
  }

  public double getEncoderPosRight() {
    return rightDrive.getPosition(); 
  }

  public void resetEncoder_LeftDrive() {

    leftDrive.resetEncoder();
  }

  public void resetEncoder_RightDrive() {
    // rightEncoder.setPosition(0);
    rightDrive.resetEncoder();
  }

  public void setSmartPosition_LeftDrive(double ref_Revs, double RPM) {
    // sets left motor to run to position
    configureSmartPosition(leftDrive.getPIDController());
    // Chassis_smartPositionKs.kP);
    leftDrive.setReferencePosition(ref_Revs, Chassis_smartPositionKs.ctrlType,
        Chassis_smartPositionKs.slot);
    leftDrive.getPIDController().setSmartMotionMaxVelocity(RPM, Chassis_smartPositionKs.slot);
  }

  public void setSmartPosition_RightDrive(double ref_Revs, double RPM) {
    // sets right motor to run to position
    configureSmartPosition(rightDrive.getPIDController());
    // configureSmartPosition(rightPidC);
    rightDrive.setReferencePosition(ref_Revs, Chassis_smartPositionKs.ctrlType,
        Chassis_smartPositionKs.slot);
    rightDrive.getPIDController().setSmartMotionMaxVelocity(RPM, Chassis_smartPositionKs.slot);
  }

  public void smartPosition_steerStraight(double leftRPM, double rightRPM, double tol) {
    // steer the robot by changing the smartMotion velocity of the left and right
    // sides of the chassis
    double loTol = 1 - tol;
    double hiTol = 1 + tol;

    // sets max and min motion velocity to steer while driving to position
    leftDrive.getPIDController().setSmartMotionMaxVelocity(leftRPM * hiTol, Chassis_smartPositionKs.slot);
    rightDrive.getPIDController().setSmartMotionMaxVelocity(rightRPM * hiTol, Chassis_smartPositionKs.slot);

    // leftPidC.setSmartMotionMinOutputVelocity(leftRPM * loTol,
    // Chassis_smartPositionKs.slot);
    // rightPidC.setSmartMotionMinOutputVelocity(rightRPM * loTol,
    // Chassis_smartPositionKs.slot);

  }

  public boolean smartPosition_isDoneLeft(double desired_Revs, double Tol) {

    double currPos = getEncoderPosLeft();
    return CommonLogic.isInRange(currPos, desired_Revs, Tol);
  }

  public boolean smartPosition_isDoneRight(double desired_Revs, double Tol) {

    double currPos = getEncoderPosRight();
    return CommonLogic.isInRange(currPos, desired_Revs, Tol);
  }

  public boolean smartPosition_LR_isDone(double desired_Revs, double Tol) {

    return (CommonLogic.isInRange(getEncoderPos_LR(), desired_Revs, Tol));

  }

  public double inches2MotorRevs(double inches) {
    // convert inches to motor Revs
    return (inches / wheelDiameter / Math.PI * gearBoxRatio);
    // 72 / 6 / Math.P * 8.45 = 32.2766224
  }

  public double inches_sec2RPM(double inches_sec) {
    // converts inches/sec to Revs/minute
    return inches2MotorRevs(inches_sec) * 60;
  }

  public void stop() {

    leftDrive.set(0);
    rightDrive.set(0);
    leftDrive.getPIDController().setIAccum(0);
    rightDrive.getPIDController().setIAccum(0);

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new cmdTeleDrive());

  }

  @Override
  public void periodic() {

  }

}