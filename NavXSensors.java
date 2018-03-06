package org.usfirst.frc.team1493.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class NavXSensors {
	private AHRS ahrs;
	double accelYPrev=0, accelXPrev=0;
	
	public NavXSensors() {
	    try {
	    	ahrs = new AHRS(SPI.Port.kMXP); 
	    	ahrs.reset();
	      } catch (RuntimeException ex ) {
	          DriverStation.reportError("NavX Error:  " + ex.getMessage(), true);
	      }	
	}
	
	public double getAngle() {
		return ahrs.getAngle();
	}
	
	public double getAccelMag() {
		return Math.sqrt( Math.pow(ahrs.getWorldLinearAccelX(),2) + Math.pow(ahrs.getWorldLinearAccelY(),2));
	}

	public double getAccelX() {
		return ahrs.getWorldLinearAccelX();
	}


	public double getAccelY() {
		return ahrs.getWorldLinearAccelY();
	}
	

	public double getJerkX() {
		double accelX = getAccelX();
		double jerkX = accelX-accelXPrev;
		accelXPrev = accelX;
		return jerkX;
	}

	
	public double getJerkY() {
		double accelY = getAccelY();
		double jerkY = accelY-accelYPrev;
		accelYPrev = accelY;
		return jerkY;
	}

	
}
