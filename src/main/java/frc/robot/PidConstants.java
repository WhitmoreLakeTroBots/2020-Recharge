package frc.robot;

import com.revrobotics.ControlType;

public final class PidConstants {

  public static class Chassis_teleOpMotionKs {
    public final static int slot = 1;
    public final static double kP = 8e-2;//5e-4;
    public final static double kI = 5e-5;
    public final static double kD = 16e-6;
    public final static double kIz = 0;
    public final static double kFF = 0;//0.000156;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
    public final static int maxRPM = 5000;
    public final static int minRPM = 0;
    public final static int maxAcc = maxRPM * 10;
    public final static int allowedErr = 1;
    public final static ControlType ctrlType = ControlType.kVelocity;
  }


}

