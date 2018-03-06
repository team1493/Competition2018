package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Winch {
	WPI_TalonSRX winch1 = new WPI_TalonSRX(0);
	WPI_TalonSRX winch2 = new WPI_TalonSRX(1);

	public Winch() {
		winch1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		winch2.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		winch1.set(ControlMode.PercentOutput,0);
		winch2.set(ControlMode.PercentOutput,0);
	}
	
	public void climb() {
		winch1.set(1);
		winch2.set(1);
	}
	
	public void descend() {
		winch1.set(-1);
		winch2.set(-1);
	}
	
	public void stop() {
		winch1.set(0);
		winch2.set(0);
	}
	
	public double getCurentA() {
		return winch1.getOutputCurrent();
	}
	
	public double getCurentB() {
		return winch2.getOutputCurrent();
	}
	

	
}
