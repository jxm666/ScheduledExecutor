package com.haima.regeneration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程 方法里一个超时，也不会影响第二个方法定时执行。
 *
 */
public class TestScheduledExecutor1 {
    private  ScheduledExecutorService scheduExec;
    
    public long start;
    
    TestScheduledExecutor1(){
        this.scheduExec =  Executors.newScheduledThreadPool(2);  
        this.start = System.currentTimeMillis();
    }
    
    public void timerOne(){
        scheduExec.schedule(new Runnable() {
            public void run() {
                System.out.println("timerOne,the time:" + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1000,TimeUnit.MILLISECONDS);
    }
    
    public void timerTwo(){
        scheduExec.schedule(new Runnable() {
            public void run() {
                System.out.println("timerTwo,the time:" + (System.currentTimeMillis() - start));
            }
        },2000,TimeUnit.MILLISECONDS);
    }
    
    public static void main(String[] args) {
    	TestScheduledExecutor1 test = new TestScheduledExecutor1();
        test.timerOne();
        test.timerTwo();
    }
}