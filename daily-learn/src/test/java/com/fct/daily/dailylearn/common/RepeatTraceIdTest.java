package com.fct.daily.dailylearn.common;

import com.fct.daily.dailylearn.utils.GenerateTraceIdUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * RepeatTraceIdTest
 *
 * @author xstarfct
 * @version 2020-09-11 17:23
 */
public class RepeatTraceIdTest {
    
    @Test
    public void repeatTest() {
        Map<String, Boolean> map = new HashMap<>();
        int max = 100000;
        for (int i = 0; i < max; i++) {
            map.put(GenerateTraceIdUtil.getTraceId(), true);
        }
        
        Assert.assertEquals(max, map.keySet().size());
    }
    
    private static final Map<String, Boolean> map = new ConcurrentHashMap<>();
    
    @Test
    public void repeatTest2() throws InterruptedException {
        int threadCount = 10;
        int cycleCount = 10000;
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < cycleCount; j++) {
                    map.put(GenerateTraceIdUtil.getTraceId(), true);
                }
            }).start();
        }
        
        Thread.sleep(3000);
        Assert.assertEquals(threadCount * cycleCount, map.keySet().size());
    }
}
