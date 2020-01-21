package frc.robot.hardware;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.ArbFFUnits;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.*;

public class wlSpark extends CANSparkMax {
    public double inverted = 1.0;

    public wlSpark(int CAN_ID, MotorType mt) {
        super(CAN_ID, mt);
    }

    public void invert() {
        inverted = inverted * -1.0;
    }

    public void inverted(boolean newValue) {
        if(newValue == true) {
            inverted = -1.0;
        }
        else{
            inverted = 1.0;
        }
    }

    public double getPosition() {
        return super.getEncoder().getPosition() * inverted;
        
    }

    public double getVelocity() {
        return super.getEncoder().getVelocity() * inverted;
    }

    public boolean isInverted() {
        if(inverted == 1.0){
            return false;
        }
        else{
            return true;
        }
    }

    public void set(double speed) {
        super.set(speed * inverted);
    }
    

    public void setReference(double value, ControlType ctrlType){
        super.getPIDController().setReference(value * inverted, ctrlType);
    }

    public void setReference(double value, ControlType ctrlType, int pidSlot){
        super.getPIDController().setReference(value * inverted, ctrlType, pidSlot);
    }

    public void setReference(double value, ControlType ctrlType, int pidSlot, int arbff){
        super.getPIDController().setReference(value, ctrlType, pidSlot, arbff);
        
    }

    public void setReference(double value, ControlType ctrlType, int pidSlot, int arbff, ArbFFUnits arbffUnits){
        
        
        super.getPIDController().setReference(value, ctrlType, pidSlot, arbff, arbffUnits);
    }
}