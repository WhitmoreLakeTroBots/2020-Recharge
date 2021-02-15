package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Auto_BangTurnByGyro extends Command {

  private boolean _isFinished = false;
  private double _requestedHeading = 0.0;
  private double _headingTol = 5.0;
  private double _leftThrottle = 0.0;
  private double _rightThrottle = 0.0;

  /**
   * Accepting a the motor velocities for left and right sides of the robot to
   * allow command to steer the robot using the default accelleration.
   *
   * @param leftThrottle  -- Left side throttle Raw motor throttle -1 to +1
   * @param rightthrottle -- Right side throttle Raw motor throttle -1 to +1
   * @param heading_deg   -- desired heading in degrees
   **/
  public Auto_BangTurnByGyro(double leftThrottle, double rightThrottle, double heading_deg) {
    requires(Robot.subChassis);
    requires(Robot.subGyro);
    _leftThrottle = leftThrottle;
    _rightThrottle = rightThrottle;
    _requestedHeading = heading_deg;
  }

  /**
   * Accepting a the motor velocities for left and right sides of the robot to
   * allow command to steer the robot using the default accelleration.
   *
   * @param leftThrottle  -- Left side throttle Raw motor throttle -1 to +1
   * @param rightthrottle -- Right side throttle Raw motor throttle -1 to +1
   * @param heading_deg   -- desired heading in degrees
   * @param heading_tol   -- tolerance for turn in degrees
   **/
  public Auto_BangTurnByGyro(double leftThrottle, double rightThrottle, double heading_deg, double headingTol) {
    requires(Robot.subChassis);
    requires(Robot.subGyro);
    _leftThrottle = leftThrottle;
    _rightThrottle = rightThrottle;
    _requestedHeading = heading_deg;
    _headingTol = headingTol;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    _isFinished = false;
    Robot.subChassis.Drive(_leftThrottle, _rightThrottle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Check to see if we are done....
    if (Robot.subGyro.gyroInTol(Robot.subGyro.getNormaliziedNavxAngle(), _requestedHeading, _headingTol)) {
      end();
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
    _isFinished = true;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    Robot.subChassis.stop();
  }
}
