/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class subLimelight extends Subsystem {
    public static NetworkTableInstance inst = null;
    //public static NetworkTable highTable = null;
    public static NetworkTableEntry ltx = null;
    public static NetworkTableEntry lty = null;
    public static NetworkTableEntry lta = null;
    public static NetworkTableEntry ltv = null;
    public static NetworkTableEntry lts = null;
    public static NetworkTableEntry ltl = null;
    public static NetworkTableEntry ltcm = null;
    public static NetworkTableEntry ltlm = null;
    public static NetworkTableEntry ltp = null;


    private NetworkTable highTable = NetworkTableInstance.getDefault().getTable("limelight");

    public subLimelight() {
        highTable.getEntry("camMode").setNumber(CAM_MODE.VISION_PROCESSING.val);

    }

    public double getTX() {
        NetworkTableEntry tx = highTable.getEntry("tx");
        return tx.getDouble(0);
    }

    public double getTY() {
        NetworkTableEntry ty = highTable.getEntry("ty");
        return ty.getDouble(0);
    }

    //Whether the limelight has any valid targets (0 or 1)
    public boolean hasTarget() {
        NetworkTableEntry tv = highTable.getEntry("tv");
        return tv.getDouble(0) == 1;
    }

    public void setLEDMode(LED_MODE ledMode) {
        highTable.getEntry("ledMode").setNumber(ledMode.val);
    }

    public void setCamMode(CAM_MODE camMode) {
        highTable.getEntry("camMode").setNumber(camMode.val);
    }

    public void setPipeline(int pipeline) {
        highTable.getEntry("pipeline").setNumber(pipeline);
    }

    public enum LED_MODE {
        OFF(1), ON(0), BLINKING(2);

        public int val;

        LED_MODE(int i) {
            val = i;
        }
    }

    public enum CAM_MODE {
        VISION_PROCESSING(0), DRIVERSTATION_FEEDBACK(1);

        public int val;

        CAM_MODE(int i) {
            val = i;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }
}