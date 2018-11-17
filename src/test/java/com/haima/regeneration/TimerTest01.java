package com.haima.regeneration;

import java.util.Timer;
import java.util.TimerTask;

enum FileEvent {  
    Delete, Create, Update 
}  

public class TimerTest01 {
    Timer timer;
    
    public TimerTest01(int time){
    	
    	System.out.print("eeeeeeeeeee == "+FileEvent.Delete.toString());
    	
        timer = new Timer();
        timer.schedule(new TimerTaskTest01(), time * 1000);
    }
    
    public static void main(String[] args) {
        System.out.println("timer begin....");
        new TimerTest01(3);
       /* for (int i = 0; i < 5; i++) {
        	new TimerTest01(3);
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}*/
        System.out.println("exit funation");
    }
}

 class TimerTaskTest01 extends TimerTask{

    public void run() {
        System.out.println("Time's up!!!!");
    }
}