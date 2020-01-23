package frc.robot.hardware;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.ArbFFUnits;
import com.revrobotics.CANSparkMax.IdleMode;
//import com.revrobotics.*;

public class wlSpark extends CANSparkMax {
  public double inverted = 1.0;
  public double encoder_zero_offset = 0.0;

  public wlSpark(int CAN_ID, MotorType motor_type) {
    super(CAN_ID, motor_type);
  }

  public void invert() {
    inverted = inverted * -1.0;
  }

  @Override
  public boolean getInverted() {
    // Do not call super.getInverted just return our local var
    if (inverted == 1.0) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void setInverted(boolean newValue) {
    // do not call super.setInverted... Just set our local var
    if (newValue == true) {
      inverted = -1.0;
    } else {
      inverted = 1.0;
    }
  }

  public void resetEncoder() {
    encoder_zero_offset = super.getEncoder().getPosition();
  }

  public double getPosition() {
    return ((super.getEncoder().getPosition() - encoder_zero_offset) * inverted);
  }

  public double getVelocity() {
    return (super.getEncoder().getVelocity() * inverted);
  }

  @Override
  public void set(double speed) {
    super.set(speed * inverted);
  }

  @Override
  public double get() {
    return (super.get() * inverted);
  }

  public void setReferenceVelocity(double value, ControlType ctrlType) {
    super.getPIDController().setReference(value * inverted, ctrlType);
  }

  public void setReferenceVelocity(double value, ControlType ctrlType, int pidSlot) {
    super.getPIDController().setReference(value * inverted, ctrlType, pidSlot);
  }

  public void setReferenceVelocity(double value, ControlType ctrlType, int pidSlot, int arbff) {
    super.getPIDController().setReference(value * inverted, ctrlType, pidSlot, arbff);
  }

  public void setReferenceVelocity(double value, ControlType ctrlType, int pidSlot, int arbff, ArbFFUnits arbffUnits) {
    super.getPIDController().setReference(value * inverted, ctrlType, pidSlot, arbff, arbffUnits);
  }

}