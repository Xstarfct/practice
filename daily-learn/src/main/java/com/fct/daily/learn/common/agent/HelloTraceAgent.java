package com.fct.daily.learn.common.agent;

/**
 * TODO
 *
 * @author xstarfct
 * @version 2020-06-24 11:29 上午
 */
public class HelloTraceAgent {
    
    public static void main(String[] args) throws InterruptedException {
        HelloTraceAgent helloTraceAgent = new HelloTraceAgent();
        while (true) {
            helloTraceAgent.sayHi("agnet");
            Thread.sleep(100);
        }
    }
    
    public String sayHi(String name) throws InterruptedException {
        sleep();
        String hi = "hi, " + name + ", " + System.currentTimeMillis();
        return hi;
    }
    
    public void sleep() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 200));
    }
}
