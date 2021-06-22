package com.fct.daily.learn.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jodd.util.concurrent.ThreadFactoryBuilder;

/**
 * CompletableFutureTest
 *
 * @author xstarfct
 * @version 2020-09-11 14:57
 */
public class CompletableFutureTest {
    
    private final static ExecutorService pool = Executors.newFixedThreadPool(10, ThreadFactoryBuilder.create().setNameFormat("-hjsgdjajhs-").get());
    
    public static void main(String[] args) {
        ;
        CompletableFuture<?>[] futures = new CompletableFuture[10];
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                System.out.println(finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return finalI;
            }, pool);
        }
        try {
            //CompletableFuture.allOf(futures).get(); 异步回调
            CompletableFuture.allOf(futures).thenRun(() -> System.out.println("全部执行完成"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("往下执行完成");
        try {
            //Random r=new Random(10);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行到最后了 ");
        
    }
}
