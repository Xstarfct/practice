package com.fct.daily.learn.common;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * CommonWithoutContainerTest
 *
 * @author xstarfct
 * @version 2020-07-14 1:56 下午
 */
@Slf4j
public class CommonWithoutContainerTest {
    
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "aaaa" + i);
        }
        System.out.println(Integer.MAX_VALUE);
        System.out.println(map.get(5));
        map.keySet().removeIf(k -> k.equals(5));
        System.out.println(JSON.toJSONString(map));
    }
    
    @Test
    public void name() {
        ArrayList<Integer> integers = Lists.newArrayList(10000);
        for (int i = 0; i < 10000; i++) {
            integers.add(i + 1);
        }
        AtomicInteger sum = new AtomicInteger();
        integers.parallelStream().forEach(e -> {
            try {
                int timeout = e % 5;
                log.info("begin e = {}, sleep = {}", e, timeout);
                TimeUnit.SECONDS.sleep(timeout);
                sum.addAndGet(e);
                log.info("end e = {}, sum = {}", e, sum.get());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        
        log.info("-------------");
    }
    
    @Test
    public void forkJoinPoolTest() {
        ForkJoinPool pool = new ForkJoinPool(100);
        ArrayList<Integer> integers = Lists.newArrayList(1000);
        for (int i = 0; i < 1000; i++) {
            integers.add(i + 1);
        }
        AtomicInteger sum = new AtomicInteger();
    
        ForkJoinTask<?> task = pool.submit(() -> integers.parallelStream().forEach(e -> {
            try {
                int timeout = e % 5;
                log.info("begin e = {}, sleep = {}", e, timeout);
                TimeUnit.SECONDS.sleep(timeout);
                sum.addAndGet(e);
                log.info("end e = {}, sum = {}", e, sum.get());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }));
        task.join();
        log.info("task.isDone() = {}", task.isDone());
        log.info("sum = {}", sum.get());
    }
}
