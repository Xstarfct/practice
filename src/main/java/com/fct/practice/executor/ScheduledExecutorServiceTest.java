package com.fct.practice.executor;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorServiceTest https://blog.csdn.net/wangmx1993328/article/details/80840598 延迟/周期性执行任务的线程池
 *
 * @author xstarfct
 * @version 2020-05-18 4:18 下午
 */
public class ScheduledExecutorServiceTest {
    
    private static final ScheduledExecutorService SERVICE = Executors.newScheduledThreadPool(10);
    
    private static final Random RANDOM = new Random();
    
    
    public static void main(String[] args) {
        
        SERVICE.scheduleAtFixedRate(() -> {
            try {
                System.out.println("任务执行开始:" + new Date());
                /**使用随机延时[0-3]秒来模拟执行任务*/
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(3));
                System.out.println("任务执行完毕:" + new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.SECONDS);
        
    }
    
    
}
