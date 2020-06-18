package com.fct.daily.dailylearn.cache;

/**
 * Cache
 *
 * @author xstarfct
 * @version 2020-06-18 4:01 下午
 */
public interface Cache {
    
    void put(Object key, Object value);
    
    Object get(Object key);
    
}
