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
    public static NetworkTable highTable = null;
    public static NetworkTableInstance inst2 = null;
    //public static NetworkTable highTable = null;
    public static NetworkTableEntry ltx2 = null;
    public static NetworkTableEntry lty2 = null;
    public static NetworkTableEntry lta2 = null;
    public static NetworkTableEntry ltv2 = null;
    public static NetworkTableEntry lts2 = null;
    public static NetworkTableEntry ltl2 = null;
    public static NetworkTableEntry ltcm2 = null;
    public static NetworkTableEntry ltlm2 = null;
    public static NetworkTableEntry ltp2 = null;
    public static NetworkTable lowTable = null;


    //private NetworkTable highTable = NetworkTableInstance.getDefault().getTable("limelight");
    public subLimelight() {
    inst = NetworkTableInstance.getDefault();
    highTable = inst.getTable("limelight-high");
    ltx = highTable.getEntry("tx");
    lty = highTable.getEntry("ty");
    lta = highTable.getEntry("ta");
    ltv = highTable.getEntry("tv");
    lts = highTable.getEntry("ts");
    ltl = highTable.getEntry("tl");
    ltcm = highTable.getEntry("camMode");
    ltlm = highTable.getEntry("ledMode");
    ltp = highTable.getEntry("pipeline");
    
    inst2 = NetworkTableInstance.getDefault();
    lowTable = inst2.getTable("limelight-low");
    ltx2 = lowTable.getEntry("tx");
    lty2 = lowTable.getEntry("ty");
    lta2 = lowTable.getEntry("ta");
    ltv2 = lowTable.getEntry("tv");
    lts2 = lowTable.getEntry("ts");
    ltl2 = lowTable.getEntry("tl");
    ltcm2 = lowTable.getEntry("camMode");
    ltlm2= lowTable.getEntry("ledMode");
    ltp2 = lowTable.getEntry("pipeline");
}
    /*
    public subLimelight() {
        highTable.getEntry("camMode").setNumber(CAM_MODE.VISION_PROCESSING.val);

    }
*/
    public double getTX() {
        return ltx.getDouble(0);
    }
    public double getTX2(){
        return ltx2.getDouble(0);
    }

    public double getTY() {
        return lty.getDouble(0);
    }

    public double getLTA2(){
        return lta2.getDouble(0);
    }
    public double getLTV2(){
        return ltv2.getDouble(0);
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
    public void setPipelinelow(int pipeline){
        lowTable.getEntry("pipeline").setNumber(pipeline);
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