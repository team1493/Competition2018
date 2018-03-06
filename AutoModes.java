package org.usfirst.frc.team1493.robot;
import edu.wpi.first.wpilibj.Timer;

public class AutoModes {
	
	public AutoModes() {
	}
	
	public void straightshoot(PIDC pidc, Intake intake, Elevator elevator){
		
		double starttime;
		starttime=Timer.getFPGATimestamp();
// jump forward, suck in cube
		pidc.driveStraightGyro(2);
		wait(0.5);
		elevator.setPostion(1);
		intake.spinIn();
		wait(1.0);
		intake.stop();
		elevator.setPostion(4);
// straight to switch
		pidc.driveStraightBump(45);
// shoot
		intake.spinOut(1);
		wait(1.0);
		intake.stop();
//  back up
		pidc.driveStraightGyro(-15);
		elevator.setPostion(1);
// turn, straight, turn,straight  to pile of cubes
		pidc.rotate(-90);
		pidc.driveStraightGyro(15);
//		pidc.rotate(-90);
		intake.spinIn();
		wait(1.0);
		intake.stop();
		elevator.setPostion(4);
		pidc.driveStraightGyro(-15);
	}
	
	public void cornershoot(PIDC pidc, Intake intake, Elevator elevator){
		
		double starttime;
		starttime=Timer.getFPGATimestamp();
// jump forward, suck in cube
		pidc.driveStraightGyro(2);
		wait(0.5);
		elevator.setPostion(1);
		intake.spinIn();
		wait(0.5);
		intake.stop();
		elevator.setPostion(4);
// straight 
		pidc.driveStraightGyro(71.5);
// turn
		pidc.rotate(90);
// straight
		pidc.driveStraightBump(1);
// shoot
		intake.spinOut(1);
		wait(1.0);
		intake.stop();
//  back up
		pidc.driveStraightGyro(-15);
		elevator.setPostion(1);
// turn, straight, turn,straight  to pile of cubes
		pidc.rotate(90);
//		pidc.driveStraightGyro(30);
//		pidc.rotate(-90);
//		pidc.driveStraightGyro(40);
//		intake.spinIn();
//		pidc.driveStraightGyro(3);
//		intake.stop();
	}
	
	
	private void wait(double delay) {
		double starttime = Timer.getFPGATimestamp();
		while(Timer.getFPGATimestamp()<starttime+delay) {
			continue;
		}
		
	}
}
