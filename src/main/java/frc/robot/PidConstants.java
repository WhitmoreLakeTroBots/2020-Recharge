package frc.robot;

public final class PidConstants {

  public static class Chassis_teleOpMotionKs {
    public final static int slot = 1;
    public final static double kP = 1e-4;//5e-4;
    public final static double kI = 1e-6;//1e-6;//1e-5;
    public final static double kD = 0;
    public final static double kIz = 0;
    public final static double kFF = 0;//0.000156;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
    public final static int maxRPM = 5000;
    public final static int minRPM = 0;
    public final static int maxAcc = maxRPM * 10;
    public final static int allowedErr = 1;

  }

  public static class Chassis_smartPositionKs {
    public final static int slot = 0;
    public final static double kP = 5e-3;
    public final static double kI = 1e-6;
    public final static double kD = 0;
    public final static double kIz = 0;
    public final static double kFF = 0.000156;
    public final static double kMaxOutput = 1;
    public final static double kMinOutput = -1;
    public final static double maxRPM = 5700;
    public final static double minRPM = 0;
    public final static double maxAcc = maxRPM * 4;
    public final static double allowedErr = maxRPM / 10;

  }

}

