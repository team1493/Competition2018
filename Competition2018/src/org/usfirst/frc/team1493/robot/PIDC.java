package org.usfirst.frc.team1493.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDC {
private NavXGyro gyro;
private SerialSonar sonar;
private FalconDrive falconDrive;

private double MAX_ROTATE_SPEED=800;
private double MAX_ROTATE_POWER=0.6;
private double MAX_STRAIGHT_POWER=0.5;

private double sg_MAX_ROTATE_SPEED=800;
private double sg_MAX_ROTATE_POWER=0.35;
private double sg_MAX_STRAIGHT_POWER=0.35;

private double KP_TURN=0.3;
private double KI_TURN=0;
private double KD_TURN=0.05;
private double KF_TURN=0;
private double KIZONE_TURN=0;
private double TOLERANCE_TURN=0.5;
private double TIMEONTARGETSET_TURN = .5;

private double KP_SRAIGHTENC_GYRO=0.0065;
private double KI_SRAIGHTENC_GYRO=0;
private double KD_SRAIGHTENC_GYRO=0.0003;
private double KF_SRAIGHTENC_GYRO=0;
private double KIZONE_SRAIGHTENC_GYRO=0;
private double KPANGLE_SRAIGHTENC_GYRO=0.1;
private double TOLERANCE_STRAIGHTENC_GYRO = 10;
private double TIMEONTARGETSET_STRAIGHTENC_GYRO = .5;

private double KP_STRAIGHTSONAR_GYRO=0.1;
private double KI_STRAIGHTSONAR_GYRO=0;
private double KD_STRAIGHTSONAR_GYRO=0.0;
private double KF_STRAIGHTSONAR_GYRO=0;
private double KIZONE_STRAIGHTSONAR_GYRO=0;
private double KPANGLE_STRAIGHTSONAR_GYRO=0.1;
private double TOLERANCE_STRAIGHTSONAR_GYRO = 1;
private double TIMEONTARGETSET_STRAIGHTSONAR_GYRO = 1;

private double target;


private ControlMode VELOCITY = ControlMode.Velocity;
Joystick joy0;

// ** remove joystick in competition code unless PID's used in telop
// ** add while Auto mode ?
	public PIDC(FalconDrive falconDrive1,NavXGyro gyro1,SerialSonar sonar1,Joystick joya) {

	joy0 = joya;
	falconDrive = falconDrive1;
	gyro=gyro1;
	sonar=sonar1;
	}
	
//**************************************	  
// PID for rotating to a specified angle
//**************************************	
	public void rotate(double target1) {
		target=target1;
		Thread t = new Thread(() -> {

		double angle1=0,angle2=0;
		double err1=0,err2=0; 
		double integral=0,rate=0;
		double rotateValue;
		double time1=0,time2=0,dt=0;
		double timeontarget=0,timeontargetstart=0;
		boolean ontarget1=false,ontarget2=false;
		boolean velmode=false;
		if(falconDrive.getmode()==VELOCITY) velmode = true;
		angle1 = gyro.getAngle(); 
		target=target+angle1;
		err1=target;
		while(timeontarget<TIMEONTARGETSET_TURN && joy0.getRawAxis(1)<.5) {
			while(Timer.getFPGATimestamp()-time1<0.005) {
				continue;
			}
			time2=time1;
			time1=Timer.getFPGATimestamp();
			ontarget2=ontarget1;
			err2=err1;
			angle2=angle1;
			dt=time1-time2;
			angle1=gyro.getAngle();
			err1=target-angle1;			
			rate = (err1-err2)/dt;
			if(Math.abs(err1)<KIZONE_TURN) integral=integral+(err1-err2)*dt; else integral=0;
			
			if (Math.abs(err1)<TOLERANCE_TURN) ontarget1=true; else ontarget1=false;					
			if(ontarget1 && !ontarget2) timeontargetstart=time1;
			if (ontarget1) timeontarget=time1-timeontargetstart;
			
			rotateValue= limit(err1*KP_TURN+integral*KI_TURN+rate*KD_TURN,MAX_ROTATE_POWER);
			
			if(velmode) rotateValue = rotateValue*MAX_ROTATE_SPEED;
			falconDrive.arcadeDrive(0, -rotateValue,false,0);	
			falconDrive.display();
//			SmartDashboard.putString("DB/String 6","Angle"+angle1);
//			SmartDashboard.putString("DB/String 7","Angle error"+err1);
//			SmartDashboard.putString("DB/String 8","Angle rotate value"+rotateValue);
//			SmartDashboard.putString("DB/String 9","KP TURN"+KP_TURN);
			
		}
		});
	t.start();
	}


	
	
	
	
//**************************************	  
// Drive straight a specified distance, using encoders and gyro to maintain heading
//**************************************		
	public void driveStraightGyro(double target1) {
		target = target1;
		Thread t = new Thread(() -> {
		double position1=0,position2=0;
		double angle1=0,targetangle;
		double err1=0,err2=0,errangle=0; 
		double integral=0,rate=0;
		double rotateValue,straightValue;
		double time1=0,time2=0,dt=0;
		double timeontarget=0,timeontargetstart=0;
		boolean ontarget1=false,ontarget2=false;
		boolean velmode=false;
		if(falconDrive.getmode()==VELOCITY) velmode = true;
		angle1 = gyro.getAngle();
		targetangle=angle1;
		position1=falconDrive.getPositionRight();
		target=position1+target;
		err1=target;
		SmartDashboard.putNumber("Error",err1);
		errangle=targetangle-angle1;
		while(timeontarget<TIMEONTARGETSET_STRAIGHTENC_GYRO && joy0.getRawAxis(1)<.5) {
			SmartDashboard.putNumber("Error",err1);
			time2=time1;
			time1=Timer.getFPGATimestamp();
			ontarget2=ontarget1;
			err2=err1;
			position2=position1;
			dt=time1-time2;
			position1=falconDrive.getPositionRight();
			err1=position1-target;
			angle1=gyro.getAngle();
			errangle=targetangle-angle1;			
			
			rate = (err1-err2)/dt;
			if(Math.abs(err1)<KIZONE_SRAIGHTENC_GYRO) integral=integral+(err1-err2)*dt; else integral=0;
			
			if (Math.abs(err1)<TOLERANCE_STRAIGHTENC_GYRO) ontarget1=true; else ontarget1=false;					
			if(ontarget1 && !ontarget2) timeontargetstart=time1;
			if (ontarget1) timeontarget=time1-timeontargetstart;
			
			straightValue= limit(err1*KP_SRAIGHTENC_GYRO+integral*KI_SRAIGHTENC_GYRO+rate*KD_SRAIGHTENC_GYRO,sg_MAX_STRAIGHT_POWER);
			rotateValue= limit(errangle*KPANGLE_SRAIGHTENC_GYRO,sg_MAX_ROTATE_POWER);
			if(velmode) {
				rotateValue = rotateValue*sg_MAX_ROTATE_SPEED;
				straightValue = straightValue*sg_MAX_ROTATE_SPEED;
			}
			falconDrive.arcadeDrive(straightValue, -rotateValue,false,0);	
			falconDrive.display();
			SmartDashboard.putNumber("Angle",angle1);
		}
		});
	t.start();
	}
	

	
	
	//**************************************	  
	// Drive straight a specified distance, use sonar and gyro to maintain heading
	//**************************************		
		public void driveStraightSonarGyro(double target1) {
			target = target1;
			Thread t = new Thread(() -> {
			double position1=0,position2=0;
			double angle1=0,targetangle;
			double err1=0,err2=0,errangle=0; 
			double integral=0,rate=0;
			double rotateValue,straightValue;
			double time1=0,time2=0,dt=0;
			double timeontarget=0,timeontargetstart=0;
			boolean ontarget1=false,ontarget2=false;
			boolean velmode=false;

			if(falconDrive.getmode()==VELOCITY) velmode = true;
			angle1 = gyro.getAngle();
			targetangle=angle1;
			position1=sonar.getRange();
			err1=target;
			errangle=targetangle-angle1;
			SmartDashboard.putNumber("Error",err1);
			while(timeontarget<TIMEONTARGETSET_STRAIGHTSONAR_GYRO && joy0.getRawAxis(1)<.5) {
				SmartDashboard.putNumber("Error",err1);
				time2=time1;
				time1=Timer.getFPGATimestamp();
				ontarget2=ontarget1;
				err2=err1;
				position2=position1;
				dt=time1-time2;
				position1=sonar.getRange();
				err1=target-position1;
				
				angle1=gyro.getAngle();
				errangle=targetangle-angle1;			
				
				rate = (err1-err2)/dt;
				if(Math.abs(err1)<KIZONE_TURN) integral=integral+(err1-err2)*dt; else integral=0;
				
				if (Math.abs(err1)<TOLERANCE_STRAIGHTSONAR_GYRO) ontarget1=true; else ontarget1=false;					
				if(ontarget1 && !ontarget2) timeontargetstart=time1;
				if (ontarget1) timeontarget=time1-timeontargetstart;
				
				straightValue= limit(err1*KP_STRAIGHTSONAR_GYRO+integral*KI_STRAIGHTSONAR_GYRO+rate*KD_STRAIGHTSONAR_GYRO,MAX_STRAIGHT_POWER);
				rotateValue= limit(errangle*KPANGLE_STRAIGHTSONAR_GYRO,MAX_ROTATE_POWER);
				
				if(velmode) {
					rotateValue = rotateValue*MAX_ROTATE_SPEED;
					straightValue = straightValue*MAX_ROTATE_SPEED;
				}
				falconDrive.arcadeDrive(straightValue, -rotateValue,false,0);	
				SmartDashboard.putString("DB/String 6","rot val "+ rotateValue);
				SmartDashboard.putString("DB/String 7","err angle"+errangle);
				SmartDashboard.putString("DB/String 8","KP Turn"+ KP_TURN);


			}
			});
			t.start();
		}
		
	
//**************************************	  
// Limit the output 
//**************************************
	  protected static double limit(double num, double lim) {
		    if (num > lim) {
		      return lim;
		    }
		    if (num < -lim) {
		      return -lim;
		    }
		    return num;
		  }

		public void setPIDConstants() {
			// FOR TUNING - Remove once completing
//			KP_TURN=SmartDashboard.getNumber("DB/Slider 0", 0);
//			KI_TURN=SmartDashboard.getNumber("DB/Slider 1", 0);
//			KD_TURN=SmartDashboard.getNumber("DB/Slider 2", 0);
//			KIZONE_TURN=SmartDashboard.getNumber("DB/Slider 3", 0);
			KP_SRAIGHTENC_GYRO=SmartDashboard.getNumber("DB/Slider 0", 0);
			KI_SRAIGHTENC_GYRO=SmartDashboard.getNumber("DB/Slider 1", 0);
			KD_SRAIGHTENC_GYRO=SmartDashboard.getNumber("DB/Slider 2", 0);
			KIZONE_SRAIGHTENC_GYRO=SmartDashboard.getNumber("DB/Slider 3", 0);

				}


	  
}

	