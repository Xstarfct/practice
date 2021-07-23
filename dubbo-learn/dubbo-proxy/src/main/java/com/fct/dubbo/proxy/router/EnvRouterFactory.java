package com.fct.dubbo.proxy.router;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Constants;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.cluster.RouterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * EnvRouterFactory
 *
 * @author fct
 * @date 2021-07-23 16:41
 */
@Activate(order = 300)
public class EnvRouterFactory implements RouterFactory {
  private static final String NAME = "envRouter";
  private static final Logger LOGGER = LoggerFactory.getLogger(EnvRouterFactory.class);
  private static final String KKL_SYSTEM_PROPERTIES = "system.properties";
  private static final String ENABLE_ENV_ROUTER = "dubbo.router.mode";

  static String getPropertyWithEnv(String key, String defaultValue) {
    Properties properties = ConfigUtils.loadProperties(KKL_SYSTEM_PROPERTIES);
    ConfigUtils.addProperties(properties);
    String v = ConfigUtils.getProperty(key);
    if (v == null || v.trim().isEmpty()) {
      v = System.getenv(key);
      if (v == null || v.trim().isEmpty()) {
        v = System.getenv(key.replaceAll("\\.", "_"));
        if (v == null || v.trim().isEmpty()) {
          v = defaultValue;
        }
      }
    }
    return v;
  }

  @Override
  public Router getRouter(URL url) {
    if (StringUtils.isEquals(getPropertyWithEnv(ENABLE_ENV_ROUTER, null), NAME)) {
      LOGGER.debug("路由功能开启: "
          + "\r\n |\tconsumer :" + URL.decode(url.getParameter(Constants.REFER_KEY))
          + EnvRouter.INSTANCE);
      return EnvRouter.INSTANCE;
    }
    return new Router() {
      @Override
      public URL getUrl() {
        return url;
      }

      @Override
      public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        return invokers;
      }

      @Override
      public boolean isRuntime() {
        return false;
      }

      @Override
      public boolean isForce() {
        return false;
      }

      @Override
      public int getPriority() {
        return 0;
      }
    };
  }
}
