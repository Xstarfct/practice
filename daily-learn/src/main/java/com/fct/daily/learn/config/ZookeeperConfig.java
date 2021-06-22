package com.fct.daily.learn.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Configuration;

/**
 * ZookeeperConfig
 *
 * @author xstarfct
 * @version 2020-06-03 10:21 上午
 */
@Configuration
@Slf4j
public class ZookeeperConfig {
    
    private static CuratorFramework client = null;
    
    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        //重试策略，初试时间1秒，重试10次
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 2);
        //通过工厂创建Curator
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .connectionTimeoutMs(60000)
                .sessionTimeoutMs(3000)
                .retryPolicy(policy).build();
        //开启连接
        client.start();
        log.info(".... zookeeper 初始化完成...");
    }
    
    public static CuratorFramework getClient() {
        return client;
    }
    
    public static void closeClient() {
        if (client != null) {
            client.close();
        }
    }
}
