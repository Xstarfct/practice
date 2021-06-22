package com.fct.daily.learn.common;

import com.alibaba.fastjson.JSON;
import com.fct.daily.learn.modal.ThreadLocalModel;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * ThreadLocalModelTest
 *
 * @author xstarfct
 * @version 2020-10-16 11:35
 */
public class ThreadLocalModelTest {
    
    private static final ThreadLocal<ThreadLocalModel> THREAD_LOCAL = ThreadLocalModel.THREAD_LOCAL;
    
    ExecutorService pool = Executors.newFixedThreadPool(10);
    
    @Test
    public void test() throws Exception {
        System.out.println(JSON.toJSONString(THREAD_LOCAL.get()));
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            pool.execute(() -> {
                THREAD_LOCAL.set(new ThreadLocalModel.Builder().now(new Date()).build());
                THREAD_LOCAL.get().setProperties("test", finalI);
                System.out.println(Thread.currentThread().getName() + ", i=" + finalI + ",test=" + THREAD_LOCAL.get().getProperties("test"));
            });
        }
        TimeUnit.SECONDS.sleep(20);
        System.out.println(JSON.toJSONString(THREAD_LOCAL));
    }
}
