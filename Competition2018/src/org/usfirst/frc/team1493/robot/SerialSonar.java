package org.usfirst.frc.team1493.robot;
import edu.wpi.first.wpilibj.SerialPort;

public class SerialSonar {
	SerialPort sp;
	byte[] bytes = new byte[6];
	int int0,int1,int2,int3,int4;
	double range;
	double time1,time2,frequency;
	
	public SerialSonar() {
		sp = new SerialPort(9600,SerialPort.Port.kMXP,8,SerialPort.Parity.kNone,
				SerialPort.StopBits.kOne);
		sp.reset();
		
		Thread t = new Thread(() -> {
			while (true) {
				while ((sp.getBytesReceived()) < 6) {
					continue;
				}
				bytes=sp.read(6);
// Range data comes in chunks of 6 bytes
// byte 0 is just a flag - ASCII "R"
// bytes 1-4 are the digits of the range in ASCII
// byte 5 is an ASCII carraige return				
				int0=(int)bytes[0];
				int1=(int)bytes[1]-48;
				int2=(int)bytes[2]-48;
				int3=(int)bytes[3]-48;
				int4=(int)bytes[4]-48;				
				range = (1000*int1+100*int2+10*int3+int4)/25.4;
			}
			});
		t.start();
	}
	
	public double getRange() {
		return range;
	}
		
}
