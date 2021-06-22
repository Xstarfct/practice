package com.fct.daily.learn.redission;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * RedissonClient
 *
 * @author xstarfct
 * @version 2020-06-15 5:23 下午
 */
@Slf4j
public class LockTest {
    
    private static final RedissonClient redissonClient;
    
    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        redissonClient = Redisson.create(config);
    }
    
    @Test
    public void testLock() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                try {
                    lockLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        TimeUnit.MINUTES.sleep(30);
    }
    
    public void lockLock() throws Exception {
        RLock lock = redissonClient.getLock("test");
        lock.lock(10, TimeUnit.SECONDS);
    
        int timeout = new Random().nextInt(12);
        TimeUnit.SECONDS.sleep(timeout);
        log.info("线程：{} 等待了 {} 秒", Thread.currentThread().getName(), timeout);
        
        lock.unlock();
    }
}
