/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1493.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Joystick joy0 = new Joystick(0);
	Joystick joy1 = new Joystick(1);	
	FalconDrive falconDrive = new FalconDrive();
	Winch winch = new Winch();
	Arm arm = new Arm();
	Intake intake= new Intake();
	Elevator elev = new Elevator();
	
	NavXGyro gyro = new NavXGyro();
	AnalogSonar sonar = new AnalogSonar(1);
	PIDC pidc = new PIDC(falconDrive,gyro,sonar,joy0);
	
	int elevPosition=1, armPosition=1;
	boolean elevButtonUpPrev=false,elevButtonDownPrev=false;
	boolean armButtonPrev = false;
	

	@Override
	public void robotInit() {
	    falconDrive.setBrakeMode();
	    falconDrive.setPercentVbusMode();

		}


	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}


	@Override
	public void teleopPeriodic() {
		double axis1,axis3,axis4;


		
// Drive Winch		
		if (joy0.getRawButton(5)) winch.climb(); 
		else if(joy0.getRawButton(6)) winch.descend();
		else winch.stop();

// Drive Elevator		
		if (joy0.getRawButton(1) && !elevButtonDownPrev) {
			if(elevPosition>1) {
				elevPosition--;
				elev.setPostion(elevPosition);
			}
		}
		elevButtonDownPrev=joy0.getRawButtonPressed(1);
		
		if (joy0.getRawButton(2) && !elevButtonUpPrev) {
			if(elevPosition<3) {
				elevPosition++;
				elev.setPostion(elevPosition);
			}
		}
		elevButtonUpPrev=joy0.getRawButtonPressed(2);
		
// Drive Arm
		if (joy0.getRawButton(3) && !armButtonPrev) {
			if(armPosition==1) armPosition=2;
			else armPosition = 1;	
			arm.setPostion(armPosition);
			}
		armButtonPrev=joy0.getRawButtonPressed(3);


// Drive Intake
		if (joy0.getRawButton(7) ) intake.spinIn();
		else if(joy0.getRawButton(8)) intake.spinOut();
		else intake.stop();

		
		
		
// Drive Wheels		
		axis1=joy0.getRawAxis(1);
		axis4=joy0.getRawAxis(4);
		falconDrive.arcadeDrive(axis1, -axis4,true);	

		
		
	}
}
