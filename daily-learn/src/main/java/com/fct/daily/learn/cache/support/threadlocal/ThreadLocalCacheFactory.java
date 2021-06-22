package com.fct.daily.learn.cache.support.threadlocal;

import com.fct.daily.learn.cache.Cache;
import com.fct.daily.learn.cache.support.AbstractCacheFactory;

/**
 * ThreadLocalCacheFactory
 *
 * @author xstarfct
 * @version 2020-06-18 4:20 下午
 */
public class ThreadLocalCacheFactory extends AbstractCacheFactory {
    
    @Override
    protected Cache createCache(Object key) {
        return new ThreadLocalCache();
    }
}
