package org.usfirst.frc.team1493.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
	
public class FalconDrive {
	WPI_TalonSRX fl = new WPI_TalonSRX(7);
	WPI_TalonSRX fr = new WPI_TalonSRX(4);
	WPI_TalonSRX bl = new WPI_TalonSRX(3);
	WPI_TalonSRX br = new WPI_TalonSRX(5);

	
	private final double RAMPRATE_OPEN=.25;
	
	private final double LCorrection = 0.1;
	private final double RCorrection = 0.1;
	private final double MaxRotation = 0.8;
	
	private double KP_VEL=0; 
	private double KI_VEL=0; 
	private double KD_VEL=0; 
	private double KF_VEL=1;
	private int KIZONE_VEL=0;
	private int MAXRPM = 400;

	private double KP_POS=0; 
	private double KI_POS=0; 
	private double KD_POS=0; 
	private double KF_POS=0;
	private int KIZONE_POS=0;
	
	private final int TIMEOUT=10;
	private int MAGIC_CRUISE_V=450;
	private int MAGIC_CRUISE_A=450;
	
	private ControlMode VBUS = ControlMode.PercentOutput;
	private ControlMode VELOCITY = ControlMode.Velocity;
	private ControlMode POSITION = ControlMode.Position;
	private ControlMode MAGIC= ControlMode.MotionMagic;
	private ControlMode FOLLOWER = ControlMode.Follower;
	
	public FalconDrive() {
		bl.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0,0);
		br.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0,0);

	
		
		bl.configNominalOutputForward(0, TIMEOUT);
		br.configNominalOutputForward(0, TIMEOUT);
		fl.configNominalOutputForward(0, TIMEOUT);
		fr.configNominalOutputForward(0, TIMEOUT);
		
		bl.configPeakOutputForward(1.0, TIMEOUT);
		br.configPeakOutputForward(1.0, TIMEOUT);
		fl.configPeakOutputForward(1.0, TIMEOUT);
		fr.configPeakOutputForward(1.0, TIMEOUT);
		
		bl.configPeakOutputReverse(-1.0, TIMEOUT);
		br.configPeakOutputReverse(-1.0, TIMEOUT);
		fl.configPeakOutputReverse(-1.0, TIMEOUT);
		fr.configPeakOutputReverse(-1.0, TIMEOUT);

		br.setSensorPhase(false);		
		
		br.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
		bl.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
		fr.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
		fl.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
	
	}

//**************************************	  
// Set Talon mode to velocity mode
//**************************************	
	public void setVelocityMode() {
		stopMotors();
		bl.set(VELOCITY,0);
		br.set(VELOCITY,0);
		fl.set(FOLLOWER, bl.getDeviceID());
		fr.set(FOLLOWER, br.getDeviceID());

		bl.config_kP(0, KP_VEL, TIMEOUT);
		bl.config_kI(0, KI_VEL, TIMEOUT);
		bl.config_kD(0, KD_VEL, TIMEOUT);
		bl.config_kF(0, KF_VEL, TIMEOUT);
		bl.config_IntegralZone(0, KIZONE_VEL, TIMEOUT);
		br.config_kP(0, KP_VEL, TIMEOUT);
		br.config_kI(0, KI_VEL, TIMEOUT);
		br.config_kD(0, KD_VEL, TIMEOUT);
		br.config_kF(0, KF_VEL, TIMEOUT);
		br.config_IntegralZone(0, KIZONE_VEL, TIMEOUT);


	}
	
//**************************************	  
// Set Talon mode to position mode
//**************************************	
	public void setPositionMode(double target) {
		stopMotors();
		bl.set(POSITION,target);
		br.set(POSITION,target);
		fl.set(FOLLOWER, bl.getDeviceID());
		fr.set(FOLLOWER, br.getDeviceID());

		bl.config_kP(0, KP_POS, TIMEOUT);
		bl.config_kI(0, KI_POS, TIMEOUT);
		bl.config_kD(0, KD_POS, TIMEOUT);
		bl.config_kF(0, KF_POS, TIMEOUT);
		bl.config_IntegralZone(0, KIZONE_POS, TIMEOUT);
		br.config_kP(0, KP_POS, TIMEOUT);
		br.config_kI(0, KI_POS, TIMEOUT);
		br.config_kD(0, KD_POS, TIMEOUT);
		br.config_kF(0, KF_POS, TIMEOUT);
		br.config_IntegralZone(0, KIZONE_POS, TIMEOUT);

	}	



//**************************************	  
// Set Talon mode to Motion Magic mode
//**************************************	
		public void setMotionMagicMode(double target) {
			stopMotors();
			fl.set(FOLLOWER, 3);
			fr.set(FOLLOWER, 2);
			bl.set(MAGIC,target);		
			bl.set(MAGIC,target);		

			bl.configMotionCruiseVelocity(MAGIC_CRUISE_V, TIMEOUT);
			br.configMotionCruiseVelocity(MAGIC_CRUISE_V, TIMEOUT);
			bl.configMotionAcceleration(MAGIC_CRUISE_A, TIMEOUT);
			br.configMotionAcceleration(MAGIC_CRUISE_A, TIMEOUT);
			
		}
	
	
//**************************************	  
// Set motor mode to Percent Vbus
//**************************************	
	public void setPercentVbusMode() {
		stopMotors();	
		bl.set(VBUS,0);		
		br.set(VBUS,0);		
		fl.set(VBUS,0);		
		fr.set(VBUS,0);		
	}

	
//**************************************	  
// Return the talon control mode 
//**************************************		
	public com.ctre.phoenix.motorcontrol.ControlMode getmode() {
		return br.getControlMode();
	}

	

//**************************************	  
//Stop Motors
//**************************************	
	public void stopMotors() {
		bl.set(bl.getControlMode(),0,TIMEOUT);
		br.set(bl.getControlMode(),0,TIMEOUT);
		if (bl.getControlMode()==VBUS) {
			fl.set(bl.getControlMode(),0,TIMEOUT);
			fr.set(bl.getControlMode(),0,TIMEOUT);		}
	}
	

//**************************************	  
//Set brake mode
//**************************************	
	public void setBrakeMode() {
		fl.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		fr.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		bl.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		br.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
	}
	

//**************************************	  
// display Talon parameters
//**************************************
	public void display(){
		SmartDashboard.putNumber("Output Current Left" ,bl.getOutputCurrent());
		SmartDashboard.putNumber("Output Current Right" ,br.getOutputCurrent());
		SmartDashboard.putNumber("Output Current F Left" ,fl.getOutputCurrent());
		SmartDashboard.putNumber("Output Current F Right" ,fr.getOutputCurrent());
		try {
			
		SmartDashboard.putNumber("Velocity Left" ,getVelocityLeft());
		SmartDashboard.putNumber("Velocity Right" ,getVelocityRight());
		SmartDashboard.putNumber("Position Left" ,getPositionLeft());
		SmartDashboard.putNumber("Position Right" ,getPositionRight());}
		catch(Exception e) {
		}
		SmartDashboard.putNumber("Closed Loop Err Left" ,bl.getClosedLoopError(10));
		SmartDashboard.putNumber("Closed Loop Err Right" ,br.getClosedLoopError(10));		
	}

//**************************************
// arcade drive method from RobotDrive class in WPI library	
//**************************************	
	  public void arcadeDrive(double moveValue, double rotateValue, boolean squared, int m) {
		    double leftMotorSpeed;
		    double rightMotorSpeed;
		    double lrcorrection;
		    moveValue = limit(moveValue);
		    lrcorrection = (moveValue>0) ? LCorrection : RCorrection;
		    rotateValue = MaxRotation*rotateValue+lrcorrection*moveValue;
		    rotateValue = -limit(rotateValue);
		    
		    
// square the inputs (while preserving the sign) to increase fine control
// while permitting full power
		    if (squared) {
		      if (moveValue >= 0.0) {
		        moveValue = moveValue * moveValue;
		      } else {
		        moveValue = -(moveValue * moveValue);
		      }
		      if (rotateValue >= 0.0) {
		        rotateValue = rotateValue * rotateValue;
		      } else {
		        rotateValue = -(rotateValue * rotateValue);
		      }
		    }
/*
		    if (Math.abs(rotateValue) > .05) {
		    	br.configOpenloopRamp(0, TIMEOUT); 
				bl.configOpenloopRamp(0, TIMEOUT);
				fr.configOpenloopRamp(0, TIMEOUT);
				fl.configOpenloopRamp(0, TIMEOUT);
		    } else {
		    	br.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
				bl.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
				fr.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
				fl.configOpenloopRamp(RAMPRATE_OPEN, TIMEOUT);
		    }
*/		    
		    if (moveValue > 0.0) {
		      if (rotateValue > 0.0) {
		        leftMotorSpeed = moveValue - rotateValue;
		        rightMotorSpeed = Math.max(moveValue, rotateValue);
		      } else {
		        leftMotorSpeed = Math.max(moveValue, -rotateValue);
		        rightMotorSpeed = moveValue + rotateValue;
		      }
		    } else {
		      if (rotateValue > 0.0) {
		        leftMotorSpeed = -Math.max(-moveValue, rotateValue);
		        rightMotorSpeed = moveValue + rotateValue;
		      } else {
		        leftMotorSpeed = moveValue - rotateValue;
		        rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
		      }
		    }

		    if(m==0) {
			  fl.set(VBUS, -limit(leftMotorSpeed));
			  bl.set(VBUS,-limit(leftMotorSpeed));
			  fr.set(VBUS,limit(rightMotorSpeed));
			  br.set(VBUS,limit(rightMotorSpeed));
		    }		    
		    if(m==1) {
		    	bl.set(VELOCITY,-MAXRPM*limit(leftMotorSpeed));		    
		    	br.set(VELOCITY,MAXRPM*limit(rightMotorSpeed));
		    	
		    }
		  }

//**************************************	  
// Limit the output to +/- 1
//**************************************
	  protected static double limit(double num) {
		    if (num > 1.0) {
		      return 1.0;
		    }
		    if (num < -1.0) {
		      return -1.0;
		    }
		    return num;
		  }
	  
	
	public double getPositionLeft() {
		return bl.getSelectedSensorPosition(0);
	}
	
	public double getPositionRight() {
		return -br.getSelectedSensorPosition(0);
	}

	
	public double getVelocityLeft() {
		return bl.getSelectedSensorVelocity(0);
	}
	
	public double getVelocityRight() {
		return -br.getSelectedSensorVelocity(0);
	}
	
	public double getClosedLoopErrorRight() {
		return -br.getClosedLoopError(0);
	}
	public double getClosedLoopErrorLeft() {
		return -bl.getClosedLoopError(0);
	}

	public void resetEncpoders() {
		// How do you do reset encoder count to zero ?
	}

	public void setPIDConstants() {
	// FOR TUNING - Remove once completing
    KP_VEL = SmartDashboard.getNumber("DB/Slider 0", 0);
    KD_VEL=SmartDashboard.getNumber("DB/Slider 1", 0);
    KF_VEL=SmartDashboard.getNumber("DB/Slider 2", 0);
    MAXRPM=(int)( (1000*SmartDashboard.getNumber("DB/Slider 3", 0) ));
	  bl.config_kP(0, KP_VEL, TIMEOUT) ; 
	  bl.config_kD(0, KD_VEL, TIMEOUT) ; 
	  bl.config_kF(0, KF_VEL, TIMEOUT) ; 
	  br.config_kP(0, KP_VEL, TIMEOUT) ; 
	  br.config_kD(0, KD_VEL, TIMEOUT) ; 
	  br.config_kF(0, KF_VEL, TIMEOUT) ; 
	}


}