package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class Arm {
	WPI_TalonSRX arm = new WPI_TalonSRX(6);
	double pos1=0.2;
	double pos2 = 0.4;
	double KP=.4;
	double KI = 0;
	double KD = 0;
	double KF = 0;
	int KIZONE = 0;
	private final int TIMEOUT=10;

	
	public Arm() {
		arm.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		arm.config_kP(0, KP, TIMEOUT);
		arm.config_kI(0, KI, TIMEOUT);
		arm.config_kD(0, KD, TIMEOUT);
		arm.config_kF(0, KF, TIMEOUT);
		arm.config_IntegralZone(0, KIZONE, TIMEOUT);
		
		arm.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.Analog, 0,0);

		// *** uncomment if using limit switches attached to the dataport
//		elev.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
//		elev.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
// ***

// start match with motor off
		arm.set(ControlMode.PercentOutput,0);		
	}
	
	public void setPostion(int pos) {
		if (pos==1)arm.set(pos1);
		else arm.set(pos2);

	}
	
	
}
