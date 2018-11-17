package com.haima.regeneration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程里一个方法运行异常，也不会影响第二方法执行。
 */
public class TestScheduledExecutor2 {
	
private  ScheduledExecutorService scheduExec;
    
    public long start;
    
    TestScheduledExecutor2(){
        this.scheduExec =  Executors.newScheduledThreadPool(2);  
        this.start = System.currentTimeMillis();
    }
    
    public void timerOne(){
        scheduExec.schedule(new Runnable() {
            public void run() {
                throw new RuntimeException();
            }
        },1000,TimeUnit.MILLISECONDS);
    }
    
    public void timerTwo(){
        scheduExec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("timerTwo invoked .....");
            }
        },2000,500,TimeUnit.MILLISECONDS);
    }
    
    public static void main(String[] args) {
    	TestScheduledExecutor2 test = new TestScheduledExecutor2();
        test.timerOne();
        test.timerTwo();
    }
}
