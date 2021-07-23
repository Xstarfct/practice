package com.fct.dubbo.proxy.utils;

public class Constants {

  public static final String GROUP_KEY = "group";
  public static final String VERSION_KEY = "version";
  public static final String PATH_SEPARATOR = "/";
  public static final String PROVIDER_SIDE = "provider";

  /** 开发环境或者是测试环境需要区分路由 */
  public static final String DUBBO_ROUTER_GROUP = "dubbo.router.group";
  /** 默认是开发环境：网关环境 dev、test、pre、pro */
  public static final String DEFAULT_ENV = "dev";
}
