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
	
	private static final String kAuto1 = "Auto1", kAuto2 = "Auto2", kAuto3 = "Auto3", kAuto4 = "Auto4";
	private static final String kAuto5 = "Auto5", kAuto6 = "Auto6", kAuto7 = "Auto7", kAuto8 = "Auto8";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
// Button Assignments	

// Buttons on Joystick 0	
	int buttonWinchUp=5;
	int buttonWinchDown=6;
	
// Buttons on Joystick 1	
	int buttonIntakeIn=5;
	int buttonIntakeOut=6;
	int buttonSolenoid=7;
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
	
	NavXSensors navx = new NavXSensors();
	SerialSonar sonar = new SerialSonar();
	PIDC pidc = new PIDC(falconDrive,navx ,sonar,joy0);
	AutoModes auto = new AutoModes();
//	int elevPosition=1;
	double armInput,armSetPosition;
	double forward=0, forwardPrev=0, changeForward, changeForwardLimit, rotate;
	
	boolean armSolPosition=false;
	boolean buttonArmPrev = false, buttonSolenoidPrev=false, solPosition = false;
	

	@Override
	public void robotInit() {
	    falconDrive.setBrakeMode();
//	    falconDrive.setPercentVbusMode();
	    falconDrive.setVelocityMode();
	    CameraServer.getInstance().startAutomaticCapture();
// get the starting arm position	   
	    arm.armStop();
	    armSetPosition=arm.getPosition();
	    elev.setPostion(1);
	    
		m_chooser.addDefault("Auto1", kAuto1);
		m_chooser.addObject("Auto2", kAuto2);
		m_chooser.addObject("Auto3", kAuto3);
		m_chooser.addObject("Auto4", kAuto4);
		m_chooser.addObject("Auto5", kAuto5);
		m_chooser.addObject("Auto6", kAuto6);
		m_chooser.addObject("Auto7", kAuto7);
		m_chooser.addObject("Auto8", kAuto8);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		}
		


	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
	}

	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
		case kAuto1:
			auto.straightshoot(pidc, intake, elev);
			break;
		case kAuto2:
			auto.cornershoot(pidc, intake, elev);
			break;
		case kAuto3:
			auto.cornershoot(pidc, intake, elev);
			break;
		case kAuto4:
			auto.cornershoot(pidc, intake, elev);
			break;
		case kAuto5:
			auto.cornershoot(pidc, intake, elev);
			break;
		case kAuto6:
			auto.cornershoot(pidc, intake, elev);
			break;
		case kAuto7:
			auto.cornershoot(pidc, intake, elev);
			break;
		case kAuto8:
			auto.cornershoot(pidc, intake, elev);
			break;
		default:
			// Put default auto code here
			break;
	}
	}


	@Override
	public void teleopPeriodic() {

		if (joy0.getRawButton(2) ) {
//			arm.setPIDConstants();			
//			falconDrive.setPIDConstants();
//			pidc.setPIDConstants();
//			pidc.setPIDConstants();
//			pidc.driveStraightGyro(6000);
//			auto.straightshoot(pidc, intake, elev);
		}
		

// Drive Elevator		
		if (joy1.getRawButton(buttonElevator1)){
				elev.setPostion(1);
				intake.stop();
		}		
		else if (joy1.getRawButton(buttonElevator2)) elev.setPostion(2);
		else if (joy1.getRawButton(buttonElevator3)) elev.setPostion(3);
		else if (joy1.getRawButton(buttonElevator4)) elev.setPostion(4);		


// Drive Intake
		
		if (joy1.getRawButton(buttonIntakeIn) ) intake.spinIn();
		else if(joy1.getRawButton(buttonIntakeOut)) intake.spinOut(elev.getUserSet());
		else intake.stop();
		
		
// Drive Winch		
		if (joy0.getRawButton(buttonWinchUp)) winch.climb(); 
		else if(joy0.getRawButton(buttonWinchDown)) winch.descend();
		else winch.stop();

		
// If arm is driven below starting point turn it off.		
		armInput=-joy1.getRawAxis(5);
		if(Math.abs(armInput)>0.08 ) {
			if (armSetPosition>400) armSetPosition=armSetPosition+armInput*0.5;
			else armSetPosition=armSetPosition+armInput*1.5;
		if (armSetPosition< 283) {
			armSetPosition = 283;
			arm.armStop();
		}
		else{
			if (armSetPosition>450) armSetPosition=450;
			arm.setPositionStick(armSetPosition);
			}
		}

		
// Drive the Solenoid		
		if (joy1.getRawButton(buttonSolenoid) && !buttonSolenoidPrev) {
			armSolPosition = !armSolPosition;
			arm.setSolenoid(armSolPosition);
			}
		buttonSolenoidPrev=joy1.getRawButton(buttonSolenoid);
		 
	
// Set LED Strip		
		if(forward >0.05) ledstrip.setPattern(1);
		else if (forward<-0.05) ledstrip.setPattern(2);
		else ledstrip.setPattern(0);
		
// Drive Wheels		
		forwardPrev=forward;
		forward=joy0.getRawAxis(1);
//  Piecewise function to give mpore sensitivity at low speed		
		if (Math.abs(forward)<=0.5) forward=forward*.5;
		else forward=1.5*forward- Math.signum(forward)*0.5;
		rotate=joy0.getRawAxis(4);
		
		
// limit acceleration/deceleration		
		changeForward=forward-forwardPrev;
		if (Math.abs(forwardPrev)<0.7) {
			if(changeForward<=0) forward=Math.max(-0.7, forward);
			else forward=Math.min(0.7, forward);
		}
		else {
			if(changeForward<=0) forward=Math.max(forwardPrev-0.02, forward);
			else forward=Math.min(forwardPrev+0.02, forward);
		}
		falconDrive.arcadeDrive(forward, -rotate,true,1);	

		
/*		forward=joy0.getRawAxis(1);
		rotate=joy0.getRawAxis(4);				
		falconDrive.arcadeDrive(forward, -rotate,true,1);	
*/
		
		
		SmartDashboard.putString("DB/String 0","PosR "+falconDrive.getPositionRight());
		SmartDashboard.putString("DB/String 1","PosL "+falconDrive.getPositionLeft());
		SmartDashboard.putString("DB/String 2","Angle "+String.valueOf(   (double)((int)(10*navx.getAngle())) /10  ) );
//		SmartDashboard.putString("DB/String 3","JerkX "+String.valueOf((int)(100*navx.getJerkX())));
//		SmartDashboard.putString("DB/String 4","JerkY "+String.valueOf((int)(100*navx.getJerkY())));
		SmartDashboard.putString("DB/String 5","elev pos"+elev.getPosition());

		
//		SmartDashboard.putString("DB/String 4","FR motor set"+ falconDrive.getOutputFR());
//		SmartDashboard.putString("DB/String 5","FL motor set"+ falconDrive.getOutputFL());
		
		SmartDashboard.putString("DB/String 6","vel left "+ falconDrive.getVelocityLeft());
		SmartDashboard.putString("DB/String 7","vel right"+falconDrive.getVelocityRight());
//		SmartDashboard.putString("DB/String 8","CLE left "+ falconDrive.getClosedLoopErrorLeft());
//		SmartDashboard.putString("DB/String 9","CLE right"+falconDrive.getClosedLoopErrorRight());
		SmartDashboard.putString("DB/String 8","forward "+forward);
		SmartDashboard.putString("DB/String 9","arm pos"+arm.getPosition());
//		SmartDashboard.putString("DB/String 7","arm set"+(int)armSetPosition);
//		SmartDashboard.putString("DB/String 8","arm error"+(int)arm.getError());
//		SmartDashboard.putString("DB/String 9","arm input"+armInput);

	}
	
}
