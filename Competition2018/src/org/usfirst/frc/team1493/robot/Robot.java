/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team1493.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Joystick joy0 = new Joystick(0);
	Joystick joy1 = new Joystick(1);	
	CameraServer cam0;
	
// Button Assignments	
	int buttonWinchUp=5;
	int buttonWinchDown=6;
	int buttonIntakeIn=5;
	int buttonIntakeOut=6;
	int buttonArm=7;
	int buttonSolenoid=8;
	int buttonElevator1=1;
	int buttonElevator2=2;
	int buttonElevator3=3;
	int buttonElevator4=4;
	
	FalconDrive falconDrive = new FalconDrive();
	Winch winch = new Winch();
	Arm arm = new Arm();
	Intake intake= new Intake();
	Elevator elev = new Elevator();
	LEDStrip ledstrip = new LEDStrip();
	
	NavXGyro gyro = new NavXGyro();
	SerialSonar sonar = new SerialSonar();
	PIDC pidc = new PIDC(falconDrive,gyro,sonar,joy0);
	
	int elevPosition=1;
	double armInput,armSetPosition;
	boolean armSolPosition=false;
	boolean buttonElevator1Prev = false, buttonElevator2Prev= false;
	boolean buttonElevator3Prev = false, buttonElevator4Prev = false;
	boolean buttonArmPrev = false, buttonSolenoidPrev=false, solPosition = false;
	

	@Override
	public void robotInit() {
	    falconDrive.setBrakeMode();
	    falconDrive.setPercentVbusMode();
	    CameraServer.getInstance().startAutomaticCapture();
// get the starting arm position	   
	    arm.armStop();
	    armSetPosition=arm.getPosition();
		}


	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}


	@Override
	public void teleopPeriodic() {
		double axis1,axis4;

// Drive Elevator		
		if (joy1.getRawButton(buttonElevator1) && !buttonElevator1Prev) {
				elev.setPostion(1);
				intake.stop();
		}		
		else if (joy1.getRawButton(buttonElevator2) && !buttonElevator2Prev) {
				elev.setPostion(2);
//				intake.spinInSlow();
		}
		else if (joy1.getRawButton(buttonElevator3) && !buttonElevator3Prev) {
				elev.setPostion(3);
//				intake.spinInSlow();
		}
		else if (joy1.getRawButton(buttonElevator4) && !buttonElevator4Prev) {
				elev.setPostion(4);
//				intake.spinInSlow();
		}
				
//		elev.manualElvevator(0.5*joy1.getRawAxis(5));
		buttonElevator1Prev=joy0.getRawButton(buttonElevator1);
		buttonElevator2Prev=joy0.getRawButton(buttonElevator2);
		buttonElevator3Prev=joy0.getRawButton(buttonElevator3);
		buttonElevator4Prev=joy0.getRawButton(buttonElevator4);

// Drive Intake
		
		if (joy1.getRawButton(buttonIntakeIn) ) intake.spinIn();
		else if(joy1.getRawButton(buttonIntakeOut)) intake.spinOut();
		else intake.stop();
		
		
// Drive Winch		
		if (joy0.getRawButton(buttonWinchUp)) winch.climb(); 
		else if(joy0.getRawButton(buttonWinchDown)) winch.descend();
		else winch.stop();
		
		
// Drive Arm 
/*		if (joy1.getRawButton(buttonArm) && !buttonArmPrev) {
			if(armPosition==1) armPosition=2;
			else armPosition = 1;	
			arm.setPostion(armPosition);
			}
		buttonArmPrev=joy1.getRawButton(buttonArm);
*/
		armInput=joy1.getRawAxis(5);
		if(Math.abs(armInput)>0.05 ) armSetPosition=armSetPosition +armInput*2.5; 

		
// Drive the Solenoid		
		if (joy1.getRawButton(buttonSolenoid) && !buttonSolenoidPrev) {
			armSolPosition = !armSolPosition;
			arm.setSolenoid(armSolPosition);
			}
		buttonSolenoidPrev=joy1.getRawButton(buttonSolenoid);
		 
// Drive Wheels		
		axis1=joy0.getRawAxis(1);
		axis4=joy0.getRawAxis(4);
		falconDrive.arcadeDrive(axis1, -axis4,true);	

	
// Set LED Strip		
		if(axis1 >0.05) ledstrip.setPattern(1);
		else if (axis1 <-0.05) ledstrip.setPattern(2);
		else ledstrip.setPattern(0);
		
		
		SmartDashboard.putString("DB/String 0","PosR "+falconDrive.getPositionRight());
		SmartDashboard.putString("DB/String 1","PosL "+falconDrive.getPositionLeft());
		SmartDashboard.putString("DB/String 2","Angle "+String.valueOf(   (double)((int)(10*gyro.getAngle())) /10  ) );
		SmartDashboard.putString("DB/String 3","Range "+String.valueOf(sonar.getRange()));
		SmartDashboard.putString("DB/String 5","elev pos"+elev.getPosition());
		SmartDashboard.putString("DB/String 6","arm pos"+arm.getPosition());


	}
	
}
