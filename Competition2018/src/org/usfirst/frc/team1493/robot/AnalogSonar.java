package org.usfirst.frc.team1493.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnalogSonar {
AnalogInput ai;
private double medianvoltage,range;
private double time1,time2;
private double frequency;

private short numreads = 150;
private float[] voltage = new float[numreads+2] ;
private short[] age = new short[numreads+2];

	public AnalogSonar(int chann) {
		ai = new AnalogInput(chann);
		
		voltage[0]=-999;voltage[numreads+1]=999;
		initialize();
		
		time1=Timer.getFPGATimestamp();
		
		
		Thread t = new Thread(() -> {
			short i = 1;
			int key;
			float vin;

			while (true) {
				removeOldest();
				vin=(float)(ai.getVoltage());
				key=search(vin);
				shiftArray(key);
				voltage[key]=vin;
				age[key]=1;
				medianvoltage=voltage[numreads/2];
				i++;
				if (i%10000*numreads==0) {

					time2=Timer.getFPGATimestamp();
					frequency=((double)i)/(time2-time1)/numreads;
					time1=time2;
					i=0;
				}

			}
			
			
			});
		t.start();
	}
	
	public double getRange() {
		range = (double)((int)(63.524*10*medianvoltage))/10;
		return range;
	}

	public double getMedianVoltage() {
	return medianvoltage;
	}

	
	
	public double getFreq() {
		return frequency;
	}
	
	private void initialize() {
		int k = 1;
		while (k<= numreads) {
			voltage[k]=0;
			age[k]=(short)k;
			k++;
		}
	}


public int search(float vin) {
        
        int start = 1;
        int end = numreads;
        int mid=0;
        while (start <= end) {
            mid = (start + end) / 2;
            if (vin >voltage[mid]) {
            	if (vin<voltage[mid+1]) return mid; 
            	else start = mid+1;
            }
            else{
            	if(vin>voltage[mid-1]) return mid-1;
            	else end = mid-1;
            }            
        }
        return mid;
    }

	
	private void removeOldest() {
		short i=numreads;
		while(age[i]!=numreads) {
			age[i]++;
			i--;
		}
		while(i > 1) {

			voltage[i]=voltage[i-1];
			age[i]=(short) (age[i-1]+1);
			i--;
		}

		voltage[1]=0;
		age[1]=1;
		
		
	}

	private void shiftArray(int key) {
		short j=1;
		while (j<= key-1) {
			voltage[j]=voltage[j+1];
			age[j]=age[j+1];
			j++;
		}

	}

	private void printarray() {
		int i = 1;
		while(i<=numreads) {		
			System.out.println("i = "+i+"  v  ="+voltage[i]+"   age = "+age[i]);
			i++;

		}
	}

}
