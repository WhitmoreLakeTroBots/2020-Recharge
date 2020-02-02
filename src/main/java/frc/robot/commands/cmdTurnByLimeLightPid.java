package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommonLogic;
import frc.robot.Robot;
import frc.robot.Settings;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.State;
import frc.robot.PidConstants.Chassis_teleOpMotionKs;
import frc.robot.subsystems.subLimelight.LED_MODE;

public class cmdTurnByLimeLightPid extends Command {
  private double _degrees;
  private double _gyroOffset;
  private double _distance;
  private boolean _isFinished = false;
  private double _startTime;
  private double _absDistance;
  private double _abortTime;

  private double _turnSignum;
  private Constraints tpConstraints = new Constraints(0.0, 0.0);
  private ProfiledPIDController pPidC;

  public cmdTurnByLimeLightPid() {
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    tpConstraints.maxAcceleration = Settings.profileTurnAcceleration;
    tpConstraints.maxVelocity = Settings.profileDefaultTurnVelocity;
    _degrees = Robot.subLimelight.getTX();
    
    Robot.subLimelight.setLEDMode(LED_MODE.ON);
    Robot.subLimelight.setPipeline(0);
    System.err.println("Auto_TurnByGyro.initialize()");
    _turnSignum = Math.signum(_degrees);
    
    // start the motion
    Robot.subChassis.resetEncoder_LeftDrive();
    Robot.subChassis.resetEncoder_RightDrive();
    _distance = CommonLogic.calcArcLength(_degrees, Robot.subChassis.trackRadius);
    _absDistance = Math.abs(_distance);
    pPidC = new ProfiledPIDController(Chassis_teleOpMotionKs.kP, Chassis_teleOpMotionKs.kI, Chassis_teleOpMotionKs.kD,
        tpConstraints, .020);

    pPidC.setTolerance(Settings.profileEndTol);

    pPidC.setGoal(new State(_absDistance, tpConstraints.maxVelocity));
    _isFinished = false;
    _startTime = CommonLogic.getTime();
    _abortTime = CommonLogic.calcProfileAbortTime(_absDistance, tpConstraints.maxVelocity,
        tpConstraints.maxAcceleration);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // double encoderVal = Robot.subChassis.getEncoderAvgDistInch();
    double deltaTime = CommonLogic.getTime() - _startTime;

    double turnAngle = Robot.subGyro.getNavxAngleRaw() - _gyroOffset;
    double measurement = Math.abs(CommonLogic.calcArcLength(turnAngle, Robot.subChassis.trackRadius));
    // measurement = (Math.abs(Robot.subChassis.getEncoderPosLeft_Inches()) +
    // Math.abs(Robot.subChassis.getEncoderPosRight_Inches())) / 2;

    double finalThrottle = _turnSignum * pPidC.calculate(measurement);
    System.err.println("fThr:" + finalThrottle + " mea:" + measurement + " turnAngle:" + turnAngle);

    // drive finalThrottle speed and add/subtract turnRate to steer.
    Robot.subChassis.Drive(-1 * finalThrottle, finalThrottle);

    // Check to see if we are done....
    if (pPidC.atGoal()) {
      // The pPidC says we are done
      System.err.println("atGoal=true");
      _isFinished = true;
    }
    else if (CommonLogic.isInRange(measurement, _absDistance, Settings.profileEndTol)) {
      // Our own checks on distance traveled says we are done
      System.err.println("isInRange=true");
      _isFinished = true;
    } else if (deltaTime > _abortTime) {
      // abort if we are not finishing in time. (AKA we are close but not there yet.)
      System.err.println("exceeded abort time. " + _abortTime + " " + deltaTime);
      _isFinished = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // System.err.println("Auto_DriveByGyro.isFinished()= " + _isFinished);
    return _isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.subChassis.stop();

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    if (Robot.oi.joyDrive().getY() > 0.0) {
      end();
    }
  }
}
