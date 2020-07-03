package com.fct.daily.dailylearn.common.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * TimeHolder
 *
 * @author xstarfct
 * @version 2020-06-24 10:58 上午
 */
public class TimeHolder {
    
    private static Map<String, Long> timeCache = new HashMap<>();
    
    public static void start(String method) {
        timeCache.put(method, System.currentTimeMillis());
    }
    
    public static long cost(String method) {
        return System.currentTimeMillis() - timeCache.get(method);
    }
}
