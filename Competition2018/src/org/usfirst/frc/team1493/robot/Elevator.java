package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
	WPI_TalonSRX elev = new WPI_TalonSRX(8);
	double pos1=0.2;
	double pos2 = 0.4;
	double pos3 = 0.6;
	double KP=.4;
	double KI = 0;
	double KD = 0;
	double KF = 0;
	int KIZONE = 0;
	private final int TIMEOUT=10;

	public Elevator() {
		elev.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		elev.config_kP(0, KP, TIMEOUT);
		elev.config_kI(0, KI, TIMEOUT);
		elev.config_kD(0, KD, TIMEOUT);
		elev.config_kF(0, KF, TIMEOUT);
		elev.config_IntegralZone(0, KIZONE, TIMEOUT);
		
		elev.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.Analog, 0,0);

		// *** uncomment if using limit switches attached to the dataport
//		elev.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
//		elev.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
// ***

// home position is position 1
		elev.set(ControlMode.Position,pos1);
	}
	
	public void setPostion(int pos) {
		if (pos==3)elev.set(pos3);
		else if (pos==2)elev.set(pos2);
		else elev.set(pos1);
	}
	
	
}

