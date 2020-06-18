package com.fct.daily.dailylearn.cache;

import com.fct.daily.dailylearn.cache.support.threadlocal.ThreadLocalCache;
import com.fct.daily.dailylearn.cache.support.threadlocal.ThreadLocalCacheFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * CacheTest
 *
 * @author xstarfct
 * @version 2020-06-18 4:28 下午
 */
public class CacheTest {
    
    private final ThreadLocalCacheFactory factory = new ThreadLocalCacheFactory();
    
    @Test
    public void name() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(1, 100).forEach((i) -> {
            ThreadLocalCache cache = (ThreadLocalCache) factory.getCache(i);
            service.submit(() -> IntStream.range(1, 100).forEach((j) -> {
                cache.put(j, i * j);
                System.out.println(
                        "=======  thread=" + Thread.currentThread().getName() + ", cache[" + i + "]: key=" + j + ",value=" + cache.get(j) + " =======");
            }));
            System.out.println("------------------------------------------------");
        });
        
        TimeUnit.MINUTES.sleep(10);
        
    }
}
