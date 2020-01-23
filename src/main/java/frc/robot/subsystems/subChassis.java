package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CommonLogic;
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
    leftDrive.setInverted(false);
    rightDrive.setInverted(true);

    configureTeleOptMotion(leftDrive.getPIDController());
    configureTeleOptMotion(rightDrive.getPIDController());

    // burn in the values so they stay during a brown out.
    rightDrive.burnFlash();
    leftDrive.burnFlash();

  }

/**
 * Accepting a joystick  it will deadband and square the values
 * and pass them off to the the auton method to be used to control the 
 * chassis wheel motors
 * @param  stick joyStick that currently has control of the wheels
 */
  public void Drive(Joystick stick) {

    double joyX = CommonLogic.joyDeadBand(stick.getX(), joyDriveDeadband);
    double joyY = CommonLogic.joyDeadBand(-stick.getY(), joyDriveDeadband);

    Drive (joyY + joyX, joyY - joyX);
  }

/**
 * Accepting a percenage of the motor velocities for left and right
 * sides of the robot to allow command to steer the robot
 * @param  leftRPMPercent -- percentage of max RPM for the motors
 * @param  rightRPMPercent -- percentage of max RPM for the motors
 */
  public void Drive (double leftRPMPercent, double rightRPMPercent) {
    System.err.println("Encoder count: "+ leftDrive.getPosition() + "   " + rightDrive.getPosition());
    System.err.println("Velocity     : "+ leftDrive.getVelocity() + "   " + rightDrive.getVelocity());

    setVelocity_RightDrive(rightRPMPercent * Chassis_teleOpMotionKs.maxRPM);
    setVelocity_LeftDrive(leftRPMPercent * Chassis_teleOpMotionKs.maxRPM);

    // kill the iAccumulator if we are setting an entire side of the
    // drivetrain to zero
    if (CommonLogic.joyDeadBand(rightRPMPercent, .01) == 0.0) {
      rightDrive.getPIDController().setIAccum(0.0);
    }

    if (CommonLogic.joyDeadBand(leftRPMPercent, .01) == 0.0) {
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


  public double getEncoderPos_LR() {
    // average the 2 encoders to get the real robot position
    return ((getEncoderPosRight() + getEncoderPosLeft()) / 2);
  }

  public double getEncoderPosLeft() {
    // get the left side of the robot position
    return leftDrive.getPosition();
  }

  public double getEncoderPosRight() {
    // get the right side of the robot position
    return rightDrive.getPosition();
  }

  public void resetEncoder_LeftDrive() {
    leftDrive.resetEncoder();
  }

  public void resetEncoder_RightDrive() {
    rightDrive.resetEncoder();
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

  public double revs2Inches(double Revs){
    return Revs/gearBoxRatio/Math.PI * 6;
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