package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.CommonLogic;
import frc.robot.Settings;
import frc.robot.commands.cmdTeleDrive;
import frc.robot.hardware.wlSpark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class subChassis extends Subsystem {

  public static wlSpark leftDrive;
  public static wlSpark rightDrive;
  public static wlSpark leftFollower;
  public static wlSpark rightFollower;

  public final double joyDriveDeadband = 0.06;
  public final double driveStraightGyroKp = 2e-3;
  public final double wheelDiameter = 6.0;
  public final double wheelCircumference = wheelDiameter * Math.PI;
  public final double gearBoxRatio = 8.45; //comp robot
  //public final double gearBoxRatio = 7.31; // Fang (practice bot)
  public final double trackWidth = 24.0;
  public final double trackRadius = trackWidth / 2;



  public subChassis() {

    leftDrive = new wlSpark(Settings.CANID_subChassisLeftMaster, MotorType.kBrushless);
    rightDrive = new wlSpark(Settings.CANID_subChassisRightMaster, MotorType.kBrushless);
    leftFollower = new wlSpark(Settings.CANID_subChassisLeftFollower, MotorType.kBrushless);
    rightFollower = new wlSpark(Settings.CANID_subChassisRightFollower, MotorType.kBrushless);
    // reset factory defaults and make them persist power cycles
    leftDrive.restoreFactoryDefaults(true);
    rightDrive.restoreFactoryDefaults(true);
    leftFollower.restoreFactoryDefaults(true);
    rightFollower.restoreFactoryDefaults(true);

    //leftFollower.follow(leftDrive);
    //rightFollower.follow(rightDrive);
    
    setBrakeModeOn();
    
    leftDrive.setInverted(true);
    rightDrive.setInverted(false);

    leftDrive.setSmartCurrentLimit (Settings.REV_NEO_CurrentLimitStalledAmps);
    rightDrive.setSmartCurrentLimit (Settings.REV_NEO_CurrentLimitStalledAmps);

    // burn in the values so they stay during a brown out.
    rightDrive.burnFlash();
    leftDrive.burnFlash();

  }

  public void setBrakeModeOn (){
    setBrakeMode(IdleMode.kBrake);
  }

  public void setBrakeModeOff() {
    setBrakeMode(IdleMode.kCoast);
  }

  public void setBrakeMode (IdleMode newMode){
    leftDrive.setIdleMode(newMode);
    rightDrive.setIdleMode(newMode);
    leftFollower.setIdleMode(newMode);
    rightFollower.setIdleMode(newMode);
  }

  private void setPower_LeftDrive(double pwrPercent) {
    double power = CommonLogic.CapMotorPower(pwrPercent * Settings.Chassis_powerLeftScaler, -1, 1);
    leftDrive.set(power);
    leftFollower.set(-power);
  }

  private void setPower_RightDrive(double pwrPercent) {
    double power = CommonLogic.CapMotorPower(pwrPercent  * Settings.Chassis_powerRightScaler, -1, 1);
    rightDrive.set(power);
    rightFollower.set(power);
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
    Drive ((joyY - joyX) , (joyY + joyX) );
  }
 
  

/**
 * Accepting a percenage of the motor velocities for left and right
 * sides of the robot to allow command to steer the robot
 * @param  leftRPM -- percentage of max RPM for the motors
 * @param  rightRPM -- percentage of max RPM for the motors
 */
  public void Drive (double powerLeft, double powerRight) {
  
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

  public double getEncoderPosLeft_Inches() {
    return revs2Inches(getEncoderPosLeft());
  }
  public double getEncoderPosRight() {
    // get the right side of the robot position
    return rightDrive.getPosition();
  }

  public double getEncoderPosRight_Inches() {
    return revs2Inches(getEncoderPosRight());
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
  public void winMatch(){

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