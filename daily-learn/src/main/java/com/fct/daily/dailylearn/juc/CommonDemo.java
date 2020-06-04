package com.fct.daily.dailylearn.juc;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * CommonDemo
 * https://www.e-learn.cn/topic/2667116
 * @author xstarfct
 * @version 2020-05-15 2:38 下午
 */
public class CommonDemo {
    
    private final static ExecutorService SERVICE = Executors.newFixedThreadPool(4);
    
    public static void main(String[] args) throws Exception {
//        testCountDownLatch();
        
//        cyclicBarrierTest();
    
        semaphoreTest();
    }
    
    private static void testCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        IntStream.range(0, 10).forEach((i) -> {
            SERVICE.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            });
        });
        countDownLatch.await();
        System.out.println("---------end1--------");
    }
    
    public static void cyclicBarrierTest() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println("******");
        });
        IntStream.range(0, 10).forEach((i) -> {
            SERVICE.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            
        });
    }
    
    public static void semaphoreTest() {
        //创建时规定容量
        Semaphore semaphore = new Semaphore(3);
        IntStream.range(0, 6).forEach((i) -> SERVICE.execute(() -> {
            try {
                semaphore.acquire();
                System.out.println(i + "来了...");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(i + "走了.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }));
    }
    
    
}
