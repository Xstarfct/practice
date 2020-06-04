package com.fct.practice.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * 监听所有db的过期事件__keyevent@*__:expired"
 *
 * @author xstarfct
 * @version 2020-05-21 11:37 上午
 */
@Slf4j
//@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    
    /**
     * @param listenerContainer
     */
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    
    /**
     * 针对 redis 数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取到失效的key
        log.info("message = {}, pattern={}", message.toString(), new String(pattern));
    }
}
