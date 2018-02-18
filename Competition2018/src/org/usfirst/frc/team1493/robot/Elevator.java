package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
	WPI_TalonSRX elev = new WPI_TalonSRX(8);
	double pos1 = -290;
	double pos2 = -380;
	double pos3 = -550;
	double pos4 = -890;
	
	double KP = 6;
	double KI = 0;
	double KD = 100;
	double KF = 0;
	int KIZONE = 0;
	private final int TIMEOUT=10;

	public Elevator() {
		elev.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		elev.setInverted(true);
		elev.config_kP(0, KP, TIMEOUT);
		elev.config_kI(0, KI, TIMEOUT);
		elev.config_kD(0, KD, TIMEOUT);
		elev.config_kF(0, KF, TIMEOUT);
		elev.config_IntegralZone(0, KIZONE, TIMEOUT);
		
		elev.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.Analog, 0,0);

		elev.configPeakOutputForward(.65,10);
		elev.configPeakOutputReverse(-.65,10);
		elev.configNominalOutputForward(0, 10);
		elev.configNominalOutputReverse(0, 10);
		elev.configAllowableClosedloopError(0,10,10);

		
//		elev.configAllowableClosedloopError(0,20,10);
						 
		elev.setInverted(true);
		elev.setSensorPhase(false);	
		
		
		
		
		// *** uncomment if using limit switches attached to the dataport
//		elev.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
//		elev.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
// ***

// home position is position 1
//		elev.set(ControlMode.Position,pos1);
		elev.set(ControlMode.PercentOutput,0);
	}
	
	public void setPostion(int pos) {
		if (pos==4) elev.set(ControlMode.Position,pos4);
		else if (pos==3) elev.set(ControlMode.Position,pos3);
		else if (pos==2) elev.set(ControlMode.Position,pos2);
		else elev.set(ControlMode.Position,pos1);
	}
	
	public void manualElvevator(double stick) {
		elev.set(ControlMode.PercentOutput,stick);
	}
	
	
	public int getPosition() {
		int position;
		position=elev.getSelectedSensorPosition(0);
		return position;
	}
	
	public double getError() {
		double error;
		error=elev.getClosedLoopError(0);
		return error;
	}

	
	public double getOutput() {
		double output;
		output=elev.getMotorOutputPercent();
		return output;
	}

	
	public String getMode() {
		String a=String.valueOf( elev.getControlMode() );
		return a;
	}

	

}

