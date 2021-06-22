package com.fct.daily.learn.cache.support;

import com.fct.daily.learn.cache.Cache;
import com.fct.daily.learn.cache.CacheFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AbstractCacheFactory
 *
 * @author xstarfct
 * @version 2020-06-18 4:15 下午
 */
public abstract class AbstractCacheFactory implements CacheFactory {
    
    private final ConcurrentMap<Object, Cache> caches = new ConcurrentHashMap<>();
    
    @Override
    public Cache getCache(Object key) {
        Cache cache = caches.get(key);
        if (cache == null) {
            caches.put(key, createCache(key));
            cache = caches.get(key);
        }
        return cache;
    }
    
    protected abstract Cache createCache(Object key);
}
