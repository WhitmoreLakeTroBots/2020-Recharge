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

    private NetworkTable limeTable = NetworkTableInstance.getDefault().getTable("limelight");

    public subLimelight() {
        limeTable.getEntry("camMode").setNumber(CAM_MODE.VISION_PROCESSING.val);

    }

    //Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    public double getTX() {
        NetworkTableEntry tx = limeTable.getEntry("tx");
        return tx.getDouble(0);
    }

    //Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    public double getTY() {
        NetworkTableEntry ty = limeTable.getEntry("ty");
        return ty.getDouble(0);
    }

    //Whether the limelight has any valid targets (0 or 1)
    public boolean hasTarget() {
        NetworkTableEntry tv = limeTable.getEntry("tv");
        return tv.getDouble(0) == 1;
    }

    public void setLEDMode(LED_MODE ledMode) {
        limeTable.getEntry("ledMode").setNumber(ledMode.val);
    }

    public void setCamMode(CAM_MODE camMode) {
        limeTable.getEntry("camMode").setNumber(camMode.val);
    }

    public void setPipeline(int pipeline) {
        limeTable.getEntry("pipeline").setNumber(pipeline);
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