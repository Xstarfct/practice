package com.fct.daily.learn.common.agent;

/**
 * https://mp.weixin.qq.com/s/7mY_itwcoYjIWHi1oQz3Ow
 *
 * @author xstarfct
 * @version 2020-06-24 11:00 上午
 */
public class HelloAgent {
    
    public HelloAgent() {
    }
    
    public static void main(String[] args) throws InterruptedException {
        TimeHolder.start(args.getClass().getName() + "." + "main");
        HelloAgent helloAgent = new HelloAgent();
        helloAgent.sayHi();
//        HelloAgent helloXunChe2 = args.getClass().getName() + "." + "main";
//        System.out.println(helloAgent + ": " + TimeHolder.cost(helloAgent));
    }
    public void sayHi() throws InterruptedException {
        TimeHolder.start(this.getClass().getName() + "." + "sayHi");
        System.out.println("hi, agent");
        this.sleep();
        String var1 = this.getClass().getName() + "." + "sayHi";
        System.out.println(var1 + ": " + TimeHolder.cost(var1));
    }
    public void sleep() throws InterruptedException {
        TimeHolder.start(this.getClass().getName() + "." + "sleep");
        Thread.sleep((long)(Math.random() * 200.0D));
        String var1 = this.getClass().getName() + "." + "sleep";
        System.out.println(var1 + ": " + TimeHolder.cost(var1));
    }
}
