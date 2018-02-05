package org.usfirst.frc.team1493.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class NavXGyro {
	private AHRS ahrs;

	public NavXGyro() {
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
	
	
}
