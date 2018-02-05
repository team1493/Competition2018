package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
	WPI_TalonSRX intakeMotor = new WPI_TalonSRX(6);
	
	public Intake() {
		intakeMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		intakeMotor.set(ControlMode.PercentOutput,0);
	}
	
	public void spinIn() {
		intakeMotor.set(1);
	}
	
	public void spinOut() {
		intakeMotor.set(-1);
	}

	public void stop() {
		intakeMotor.set(0);
	}

	
}
