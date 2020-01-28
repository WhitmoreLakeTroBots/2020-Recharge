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

  public final double joyDriveDeadband = 0.06;
  public final double driveStraightGyroKp = 0.05;
  public final double wheelDiameter = 6.0;
  public final double wheelCircumference = wheelDiameter * Math.PI;
  public final double gearBoxRatio = 8.45;
  public final double trackWidth = 24.0;
  public final double trackRadius = trackWidth / 2;

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

    leftDrive.setSmartCurrentLimit (Settings.REV_NEO_CurrentLimitStalledAmps);
    rightDrive.setSmartCurrentLimit (Settings.REV_NEO_CurrentLimitStalledAmps);

    // burn in the values so they stay during a brown out.
    rightDrive.burnFlash();
    leftDrive.burnFlash();

  }
  private void setPower_LeftDrive(double pwrPercent) {
    double power = CommonLogic.CapMotorPower(pwrPercent * Settings.Chassis_powerLeftScaler, -1, 1);
    leftDrive.set(power);
  }

  private void setPower_RightDrive(double pwrPercent) {
    double power = CommonLogic.CapMotorPower(pwrPercent  * Settings.Chassis_powerRightScaler, -1, 1);
    rightDrive.set(power);
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

    Drive ((joyY + joyX) , (joyY - joyX) );
  }

/**
 * Accepting a percenage of the motor velocities for left and right
 * sides of the robot to allow command to steer the robot
 * @param  leftRPM -- percentage of max RPM for the motors
 * @param  rightRPM -- percentage of max RPM for the motors
 */
  public void Drive (double powerLeft, double powerRight) {
    //System.err.println("Encoder count: "+ leftDrive.getPosition() + "   " + rightDrive.getPosition());
    //System.err.println("Velocity     : "+ leftDrive.getVelocity() + "   " + rightDrive.getVelocity());

    setPower_RightDrive(powerRight);
    setPower_LeftDrive(powerLeft);
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

  public double getEncoder_Inches_LR () {

    return revs2Inches(getEncoderPos_LR()) ;
  }

  public double inches2MotorRevs(double inches) {
    // convert inches to motor Revs
    return (inches / wheelCircumference * gearBoxRatio);
    // 72 / 6 / Math.P * 8.45 = 32.2766224
  }

  public double inches_sec2RPM(double inches_sec) {
    // converts inches/sec to Revs/minute
    return inches2MotorRevs(inches_sec) * 60;
  }

  public double revs2Inches(double Revs){
    return (Revs / gearBoxRatio * wheelCircumference);
  }

  public void stop() {
    leftDrive.set(0);
    rightDrive.set(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new cmdTeleDrive());

  }

  @Override
  public void periodic() {

  }



}