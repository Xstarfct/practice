package com.fct.daily.dailylearn.cache.support.threadlocal;

import com.fct.daily.dailylearn.cache.Cache;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ThreadLocalCache
 *
 * @author xstarfct
 * @version 2020-06-18 4:02 下午
 */
public class ThreadLocalCache implements Cache {
    
    private ThreadLocal<Map<Object, Object>> store;
    
    
    public ThreadLocalCache() {
        this.store = ThreadLocal.withInitial(ConcurrentHashMap::new);
    }
    
    @Override
    public void put(Object key, Object value) {
        store.get().put(key, value);
    }
    
    @Override
    public Object get(Object key) {
        return store.get().get(key);
    }
}
