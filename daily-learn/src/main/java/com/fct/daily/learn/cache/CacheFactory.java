package com.fct.daily.learn.cache;

/**
 * TODO
 *
 * @author xstarfct
 * @version 2020-06-18 4:13 下午
 */
public interface CacheFactory {
    
    Cache getCache(Object key);
}
