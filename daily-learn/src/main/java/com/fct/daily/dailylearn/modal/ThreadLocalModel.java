package com.fct.daily.dailylearn.modal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程局部变量存储类，供每次dubbo请求存储公共变量
 */
public class ThreadLocalModel {
    
    public final static ThreadLocal<ThreadLocalModel> THREAD_LOCAL = new InheritableThreadLocal<>();
    private Map<String, Object> properties;
    private Date now;
    
    public ThreadLocalModel(Builder builder) {
        this.now = builder.now;
        this.properties = builder.properties;
    }
    
    public void setProperties(String key, Object val) {
        synchronized (this) {
            if (this.properties == null) {
                this.properties = new Builder().build().properties;
            }
        }
        
        this.properties.put(key, val);
    }
    
    public Object getProperties(String key) {
        return this.properties.get(key);
    }
    
    /**
     * build 模式
     */
    public static class Builder {
        
        private Date now;
        private Map<String, Object> properties = new HashMap<>();
        
        public Builder now(Date now) {
            this.now = now;
            return this;
        }
        
        public ThreadLocalModel build() {
            return new ThreadLocalModel(this);
        }
        
    }
    
    public Date getNow() {
        return now;
    }
    
    public void setNow(Date now) {
        this.now = now;
    }
}
