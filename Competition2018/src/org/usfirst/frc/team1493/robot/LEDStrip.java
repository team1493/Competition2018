// This class tells the arduino which light pattern to flash on the LED strips
// by sending 3 bits over the digital output pins

package org.usfirst.frc.team1493.robot;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay;

public class LEDStrip {
	private DigitalOutput do0 = new DigitalOutput(0);
	private DigitalOutput do1 = new DigitalOutput(1);
	private DigitalOutput do2 = new DigitalOutput(2);
	private Relay relay = new Relay(1);
				
	public LEDStrip() {
		relay.setDirection(Relay.Direction.kForward);
		relay.set(Relay.Value.kOff);
		setPattern(1);
	}
	
	public void setPattern(int i) {
		boolean pin0=false,pin1=false,pin2=false;  
		switch (i) {
          case 0:  		
        	  pin0=false;pin1=false;pin2=false;
        	  break;
          case 1:  		
        	  pin0=true;pin1=false;pin2=false;
        	  break;
          case 2:  		
        	  pin0=false;pin1=true;pin2=false;
        	  break;
          case 3:  		
        	  pin0=true;pin1=true;pin2=false;
        	  break;
          case 4:  		
        	  pin0=false;pin1=false;pin2=true;
        	  break;
          case 5:  		
        	  pin0=true;pin1=false;pin2=true;
        	  break;
          case 6:  		
        	  pin0=false;pin1=true;pin2=true;
        	  break;
          case 7:  		
        	  pin0=true;pin1=true;pin2=true;
        	  break;        	  
          default: 
        	  pin0=false;pin1=false;pin2=false;
              break;
      }
		do0.set(pin0);do1.set(pin1);do2.set(pin2);
	}
	
}
