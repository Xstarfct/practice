package com.fct.rpc.core.register.impl;

import com.fct.rpc.core.common.ServiceInfo;
import com.fct.rpc.core.register.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.io.IOException;

/**
 * ZookeeperRegistryService
 *
 * @author fct
 * @date 2021-08-09 14:25
 */
@Slf4j
public class ZookeeperRegistryService implements RegistryService {
  public static final int BASE_SLEEP_TIME_MS = 1000;
  public static final int MAX_RETRIES = 3;
  public static final String ZK_BASE_PATH = "/rpc_fct";

  private ServiceDiscovery<ServiceInfo> serviceDiscovery;

  public ZookeeperRegistryService(String registryAddress) {
    try {
      CuratorFramework client = CuratorFrameworkFactory.newClient(registryAddress, new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES));
      client.start();
      JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
      this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
          .client(client)
          .serializer(serializer)
          .basePath(ZK_BASE_PATH)
          .build();
      this.serviceDiscovery.start();
    } catch (Exception e) {
      log.error("serviceDiscovery start error", e);
    }
  }

  @Override
  public void register(ServiceInfo serviceInfo) throws Exception {
    ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
        .name(serviceInfo.getServiceName())
        .address(serviceInfo.getAddress())
        .port(serviceInfo.getPort())
        .payload(serviceInfo)
        .build();
    serviceDiscovery.registerService(serviceInstance);
  }

  @Override
  public void unRegister(ServiceInfo serviceInfo) throws Exception {
    ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance
        .<ServiceInfo>builder()
        .name(serviceInfo.getServiceName())
        .address(serviceInfo.getAddress())
        .port(serviceInfo.getPort())
        .payload(serviceInfo)
        .build();
    serviceDiscovery.unregisterService(serviceInstance);
  }

  @Override
  public void destroy() throws IOException {
    serviceDiscovery.close();
  }
}
