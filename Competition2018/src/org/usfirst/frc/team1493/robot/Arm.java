package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
	WPI_TalonSRX arm = new WPI_TalonSRX(6);
	Solenoid solUp = new Solenoid(0);
	Solenoid solDown = new Solenoid(1);
	
	double pos1=-200;     // down
	double pos2 = -615;  //up
//motor output for position control with analog pot = (kp/1023) x error 
	double KPup=7;
	double KPdown = .5;
	double KI = 0.01;
	double KDup = 1200;
	double KDdown = 900;
	double KF = 0;
	int KIZONE = 5;
	private final int TIMEOUT=10;

	
	public Arm() {
		armStop();
		arm.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		arm.config_kP(0, KPup, TIMEOUT);
		arm.config_kI(0, KI, TIMEOUT);
		arm.config_kD(0, KDup, TIMEOUT);
		arm.config_kF(0, KF, TIMEOUT);
		arm.config_IntegralZone(0, KIZONE, TIMEOUT);
		
		arm.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.Analog, 0,0);
		arm.configPeakOutputForward(.4,10);
		arm.configPeakOutputReverse(-.4,10);
		arm.configNominalOutputForward(0, 10);
		arm.configNominalOutputReverse(0, 10);
		arm.configAllowableClosedloopError(0,5,10);
		
		arm.configForwardSoftLimitEnable(false, 10); 
		arm.configReverseSoftLimitEnable(false, 10); 
		 
		arm.setInverted(true);
		arm.setSensorPhase(true);


// *** uncomment if using limit switches attached to the dataport
//		arm.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
//		arm.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, 
//				LimitSwitchNormal.NormallyOpen, 0);
// ***

// start match with motor off
		arm.set(ControlMode.PercentOutput,0);		
	}
	
	public void setPostionButton(int pos) {
		if (pos==1) {
			arm.config_kP(0, KPdown, TIMEOUT);
			arm.config_kD(0, KDdown, TIMEOUT);
			arm.set(ControlMode.Position,pos1,10);
			Thread t = new Thread(() -> {
				while(arm.getSelectedSensorPosition(0)<-210) {
					continue;
				}
				arm.set(ControlMode.PercentOutput,0);		
			});
		t.start();

		}
		else {
			arm.config_kP(0, KPup, TIMEOUT);
			arm.config_kD(0, KDup, TIMEOUT);
			arm.set(ControlMode.Position,pos2,10);			
		}
	}
	

	
	public void setPositionStick(double setpoint) {
		arm.set(ControlMode.Position,setpoint);
	}

	public void armStop() {
		arm.set(ControlMode.PercentOutput,0);
	}
	
	
	public void setSolenoid(boolean solPosition) {
		solUp.set(solPosition);
		solDown.set(!solPosition);
	}
	
	public int getPosition() {
		int position;
		position=arm.getSelectedSensorPosition(0);
		return position;
	}
	
	public double getError() {
		double error;
		error=arm.getClosedLoopError(0);
		return error;
	}

	
	public String getOutputCalledFor() {
		double output;
		output=arm.get();
		return String.valueOf(output);
	}

	
	public String getMode() {
		String a=String.valueOf( arm.getControlMode() );
		return a;
	}

	public void setPIDConstants() {
// FOR TUNING - Remove once completing
		KPup=SmartDashboard.getNumber("DB/Slider 0", 0);
		KDup=SmartDashboard.getNumber("DB/Slider 1", 0);
		KI=SmartDashboard.getNumber("DB/Slider 2", 0);
		KIZONE=(int)SmartDashboard.getNumber("DB/Slider 3", 0);
		arm.config_kP(0, KPup, TIMEOUT);
		arm.config_kD(0, KDup, TIMEOUT);
		arm.config_kI(0, KI, TIMEOUT);
		arm.config_IntegralZone(0, KIZONE, TIMEOUT);

	}
	
	
}
