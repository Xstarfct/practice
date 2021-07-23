package com.fct.dubbo.proxy.service;

import com.alibaba.fastjson.JSON;
import com.fct.dubbo.proxy.domain.ApiProxyResult;
import com.fct.dubbo.proxy.domain.BaseResponseCode;
import com.fct.dubbo.proxy.domain.ServiceDefinition;
import com.fct.dubbo.proxy.router.EnvRouter;
import com.fct.dubbo.proxy.utils.ResultCode;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class GenericInvoke {

  private static ApplicationConfig applicationConfig;
  private static final AtomicBoolean init = new AtomicBoolean(false);

  private static Registry registry;

  public static void setRegistry(Registry registry) {
    GenericInvoke.registry = registry;
  }

  private static void init() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress(
        registry.getUrl().getProtocol() + "://" + registry.getUrl().getAddress());
    registryConfig.setGroup(registry.getUrl().getParameter(CommonConstants.GROUP_KEY));
    applicationConfig = new ApplicationConfig();
    applicationConfig.setName("dubbo-proxy");
    applicationConfig.setRegistry(registryConfig);
  }

  public static Object genericCall(
      String interfaceName, String group, String version, ServiceDefinition sd) {
    if (init.compareAndSet(false, true)) {
      init();
    }
    ReferenceConfig<GenericService> reference = initReference(interfaceName, group, version);
    final ReferenceConfigCache cache = ReferenceConfigCache.getCache();

    Object result = null;
    long t1 = System.currentTimeMillis();
    try {
      final GenericService svc = cache.get(reference);
      // 支持dubbo attachments
      if (sd.getAttachments() != null
          && !sd.getAttachments().isEmpty()) {
        RpcContext.getContext().setAttachments(sd.getAttachments());
      }
      result = svc.$invoke(sd.getMethodName(), sd.getParamTypes(), sd.getParamValues());
      return result;
    } catch (Exception e) {
      log.error("Generic invoke failed", e);
      if (e instanceof RpcException) {
        RpcException e1 = (RpcException) e;
        if (e1.isTimeout()) {
          return ApiProxyResult.formatErrMsg(BaseResponseCode.PRC_ERROR, ResultCode.TIMEOUT.getMessage());
        }
        if (e1.isBiz()) {
          return ApiProxyResult.formatErrMsg(BaseResponseCode.PRC_ERROR, ResultCode.BIZ_ERROR.getMessage());
        }
        if (e1.isNetwork()) {
          return ApiProxyResult.formatErrMsg(BaseResponseCode.PRC_ERROR, ResultCode.NETWORK_ERROR.getMessage());
        }
        if (e1.isSerialization()) {
          return ApiProxyResult.formatErrMsg(BaseResponseCode.PRC_ERROR, ResultCode.SERIALIZATION.getMessage());
        }
      }
      return ApiProxyResult.formatErrMsg(BaseResponseCode.PRC_ERROR, e.getMessage());
    } finally {
      log.info(
          "dubbo generic invoke,cost={} service={}, sd={} , result={}.",
          System.currentTimeMillis() - t1,
          interfaceName,
          JSON.toJSONString(sd),
          result);
    }
  }

  private static ReferenceConfig<GenericService> initReference(
      String interfaceName, String group, String version) {
    ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
    reference.setGeneric("true");
    reference.setApplication(applicationConfig);
    reference.setGroup(group);
    reference.setVersion(version);
    reference.setInterface(interfaceName);
    // envRouter setting
    String routerGroup = RpcContext.getContext().getAttachment(EnvRouter.DUBBO_ROUTER_GROUP);
    if (!StringUtils.isBlank(routerGroup)) {
      ConsumerConfig consumerConfig = new ConsumerConfig();
      consumerConfig.setParameters(ImmutableMap.of(routerGroup, routerGroup));
      reference.setConsumer(consumerConfig);
    }
    return reference;
  }
}
