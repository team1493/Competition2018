package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
	WPI_TalonSRX intakeMotorL = new WPI_TalonSRX(2);
	WPI_TalonSRX intakeMotorR = new WPI_TalonSRX(9);
	

	public Intake() {
		intakeMotorL.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		intakeMotorL.set(ControlMode.PercentOutput,0);
		intakeMotorR.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		intakeMotorR.set(ControlMode.PercentOutput,0);
	
		intakeMotorR.setInverted(true);
		
		intakeMotorL.configNominalOutputForward(0, 10);
		intakeMotorR.configNominalOutputForward(0, 10);
		intakeMotorL.configNominalOutputReverse(0, 10);
		intakeMotorR.configNominalOutputReverse(0, 10);
		
		intakeMotorL.configPeakOutputForward(1, 10);
		intakeMotorR.configPeakOutputForward(1, 10);
		intakeMotorL.configPeakOutputReverse(-1, 10);
		intakeMotorR.configPeakOutputReverse(-1, 10);
		
	}
	
	public void spinIn() {
		intakeMotorL.set(.6);
		intakeMotorR.set(.6);
	}
	
	
	public void spinInSlow() {
		intakeMotorL.set(.25);
		intakeMotorR.set(.25);
	}
	
	public void spinOut(int pos) {
		double power=-0.6;
		if (pos==1 || pos==2) power = -0.6;
		else power = -0.6;
		intakeMotorL.set(power);
		intakeMotorR.set(power);
	}

	public void stop() {
		intakeMotorL.set(0);
		intakeMotorR.set(0);
	}

	
}
