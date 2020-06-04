package com.fct.practice.zk;

import com.alibaba.fastjson.JSON;
import com.fct.practice.PracticeApplicationTests;
import com.fct.practice.config.ZookeeperConfig;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * ZkTest https://www.cnblogs.com/june777/p/11865116.html
 *
 * @author xstarfct
 * @version 2020-06-03 10:25 上午
 */
@Slf4j
public class ZkTest extends PracticeApplicationTests {
    
    private static final String TEST_NODE = "/test";
    
    @Test
    public void createNode() {
        CuratorFramework client = ZookeeperConfig.getClient();
        try {
            // 创建节点，关联数据
            String s = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(TEST_NODE, "哈哈".getBytes(StandardCharsets.UTF_8));
            
            System.out.println("--------------- s = " + s + " ---------------");
        } catch (Exception e) {
            log.error("createNode error...", e);
        }
    }
    
    @Test
    public void getNode() {
        CuratorFramework client = ZookeeperConfig.getClient();
        try {
            byte[] bytes = client.getData().forPath(TEST_NODE);
            
            System.out.println("--------------- data = " + new String(bytes) + " ---------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void lock() {
        CuratorFramework client = ZookeeperConfig.getClient();
        // 写锁互斥、读写互斥
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/test");
    }
    
    /**
     * 通过NodeCache 来监控节点值的变化
     */
    @Test
    public void testListen() throws Exception {
        CuratorFramework client = ZookeeperConfig.getClient();
        
        NodeCache nodeCache = new NodeCache(client, TEST_NODE, false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> log.info("listener: data = {}", JSON.toJSONString(nodeCache.getCurrentData())));
        
        client.getData().usingWatcher((CuratorWatcher) event -> log.info("触发 watcher，EVENT = {}", JSON.toJSONString(event))).forPath(TEST_NODE);
        
        IntStream.range(0, 10).forEach(i -> {
            try {
                client.setData().forPath(TEST_NODE, (i + UUID.randomUUID().toString()).getBytes());
                TimeUnit.SECONDS.sleep(5L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        
    }
}
