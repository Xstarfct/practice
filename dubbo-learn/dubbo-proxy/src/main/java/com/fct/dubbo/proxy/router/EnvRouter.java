package com.fct.dubbo.proxy.router;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Constants;
import org.apache.dubbo.rpc.cluster.Router;

import java.util.ArrayList;
import java.util.List;

import static com.fct.dubbo.proxy.router.EnvRouterFactory.getPropertyWithEnv;

/**
 * EnvRouter.
 * 线程安全
 * <pre>
 * 环境路由启动方式：
 * {@code
 *      <!-- Registry配置中需要添加router参数,用来通过SPI扩展来添加EnvRouter到路由列表中, EnvRouter的SPI扩展名为envRouter，为空时不适用扩展路由(生产环境应置空) -->
 *      <dubbo:registry>
 *          <dubbo:parameter key="router" value="${dubbo_router_mode:}"/>
 *      </dubbo:registry>
 *      <!-- Provider需要添加参数dubbo.router.group, 表示提供者所属路由组 -->
 *      <dubbo:provider>
 * 		    <dubbo:parameter key="dubbo.router.group" value="${dubbo_router_group:}"/>
 * 	    </dubbo:provider>
 *      所有支持使用<dubbo:parameter>子标签的配置项都可以用来提供EnvRouter所需的参数, 如: <dubbo:consumer> <dubbo:service> <dubbo:reference>
 *      EnvRouter在进行路由时总是会优先从Consumer和Provider的URL参数中分别来获取 目标环境(dubbo.router.consumer.group) 和 提供者环境(dubbo.router.group)
 * }
 *
 * 动态路由额外配置项：
 *      1、dynamic.env.router为true开启动态路由(默认为true)，为false则仅在Provider发生变化时刷新Invoker列表
 *      2、default.env.router.group回源路由组(默认stable)，路由失败后回源环境，例如未找到dev的Provider则寻找stable的Provider
 * 以上各项属性均通过{@link ConfigUtils#getProperty(String)}获取
 *
 * 路由方式：
 *      1、非动态路由模式下，仅在注册中心服务提供者发生变化时更新路由结果
 *         非动态路由的调用方式等同于旧的dubbo路由方式, 可以参考<a href="http://wiki.ikuko.com/pages/viewpage.action?pageId=5571741">wiki</a>
 *      2、开启动态路由模式下，每次调用根据当前Provider过滤符合条件的结果
 *         动态路由示例：
 *         A -> B -> C -> D -> E, 一条调用链路, 默认的路由组{@link EnvRouter#ENV_GROUP_DEFAULT}=stable
 *         注册中心存在以下应用:
 *         A(入口)  B(stable,dev01) C(stable,dev) D(stable) E(stable,dev)
 *
 *         A的{@link EnvRouter#ENV_GROUP}==stable时:   A -> B(stable) ->  C(stable) -> D(stable) -> E(stable)
 *         A的{@link EnvRouter#ENV_GROUP}==dev时:      A -> B(stable) ->  C(dev)    -> D(stable) -> E(dev)
 *         A的{@link EnvRouter#ENV_GROUP}==dev01时:    A -> B(dev01)  ->  C(stable) -> D(stable) -> E(stable)
 *
 *         A应用可以理解为网关，如果通过对网关的简单改造, 我们也可以实现一个入口层, 路由到多个不同环境, 从而节省更多的机器资源
 * 环境获取优先级，非动态路由模式下获取顺序1->4，动态路由模式下获取顺序1->2->3-4，直至获取到非空值为止
 *      1、{@link URL#getParameter(String, String)}获取Consumer参数{@link EnvRouter#DUBBO_ROUTER_CONSUMER_GROUP}
 *          场景: 入口层，如K-Api
 *      2、{@link Invocation#getAttachment(String, String)}获取会话环境{@link EnvRouter#DUBBO_ROUTER_GROUP}
 *          场景: Consumer -> Provider
 *      3、{@link RpcContext#getAttachment(String)}获取上下文环境
 *          场景: Provider -> Consumer
 *      4、{@link EnvRouter#ENV_GROUP}
 *          场景: 入口层，如K-Api，且未配置消费者dubbo.router.consumer.group参数的值
 * 路由失败转移规则:
 *      1、根据上面获取到的环境，对所有Provider进行第一次过滤，筛选结果不为空则返回
 *      2、根据{@link ConfigUtils#getProperty(String)}中{@link EnvRouter#ENV_GROUP_DEFAULT_KEY}所配置的默认环境（为空时取{@link EnvRouter#DUBBO_ROUTER_GROUP_DEFAULT}）对所有Provider进行二次过滤，筛选结果不为空则返回
 *      3、返回所有Providers
 * </pre>
 *
 * @author Young.Z
 * @date 2018/11/27
 */
public class EnvRouter implements Router {

  public static final String DUBBO_ROUTER_GROUP = "dubbo.router.group";
  public static final String DEFAULT_DUBBO_ROUTER_GROUP = "default." + DUBBO_ROUTER_GROUP;
  private static final String DUBBO_ROUTER_CONSUMER_GROUP = "dubbo.router.consumer.group";
  private static final String DUBBO_ROUTER_GROUP_DEFAULT = "stable";

  private static final String DYNAMIC_ENV_ROUTER_KEY = "dynamic.env.router";
  private static final String ENV_GROUP_DEFAULT_KEY = "default.env.router.group";

  private static final String ENV_GROUP;
  private static final String ENV_GROUP_DEFAULT;

  private static final URL DYNAMIC_ROUTER_URL;

  static {
    ENV_GROUP_DEFAULT = getPropertyWithEnv(ENV_GROUP_DEFAULT_KEY, DUBBO_ROUTER_GROUP_DEFAULT);
    ENV_GROUP = getPropertyWithEnv(DUBBO_ROUTER_GROUP, ENV_GROUP_DEFAULT);
    DYNAMIC_ROUTER_URL = Boolean.parseBoolean(getPropertyWithEnv(DYNAMIC_ENV_ROUTER_KEY, "true")) ? null :
        new URL("router", "anyHost", 0).addParameter(Constants.RUNTIME_KEY, false);
  }

  static final EnvRouter INSTANCE = new EnvRouter();

  private EnvRouter() {
  }

  @Override
  public URL getUrl() {
    return DYNAMIC_ROUTER_URL;
  }

  @Override
  public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
    String sessionEnv;
    if (DYNAMIC_ROUTER_URL == null) {
      sessionEnv =
          url.getParameter(DUBBO_ROUTER_CONSUMER_GROUP,
              invocation.getAttachment(DUBBO_ROUTER_GROUP,
                  RpcContext.getContext().getAttachment(DUBBO_ROUTER_GROUP)));
      if (StringUtils.isBlank(sessionEnv)) {
        sessionEnv = ENV_GROUP;
      }
      invocation.getAttachments().put(DUBBO_ROUTER_GROUP, sessionEnv);
    } else {
      sessionEnv = url.getParameter(DUBBO_ROUTER_CONSUMER_GROUP, ENV_GROUP);
    }
    List<Invoker<T>> newInvokers = new ArrayList<>(invokers.size());
    for (Invoker<T> invoker : invokers) {
      String providerEnv = invoker.getUrl().getParameter(DUBBO_ROUTER_GROUP, invoker.getUrl().getParameter(DEFAULT_DUBBO_ROUTER_GROUP));
      if (StringUtils.isEquals(providerEnv, sessionEnv)) {
        newInvokers.add(invoker);
      }
    }
    if (newInvokers.isEmpty()) {
      for (Invoker<T> invoker : invokers) {
        if (ENV_GROUP_DEFAULT.equals(invoker.getUrl().getParameter(DUBBO_ROUTER_GROUP, invoker.getUrl().getParameter(DEFAULT_DUBBO_ROUTER_GROUP)))) {
          newInvokers.add(invoker);
        }
      }
    }
    if (newInvokers.isEmpty()) {
      return invokers;
    }
    return newInvokers;
  }

  @Override
  public boolean isRuntime() {
    return Boolean.parseBoolean(getPropertyWithEnv(DYNAMIC_ENV_ROUTER_KEY, "true"));
  }

  @Override
  public boolean isForce() {
    return false;
  }

  @Override
  public int getPriority() {
    return 0;
  }

  @Override
  public int compareTo(Router o) {
    return 1;
  }

  @Override
  public String toString() {
    return " \r\n |\tdynamic: " + (DYNAMIC_ROUTER_URL == null) + "\r\n" +
        " |\tenv_group: " + ENV_GROUP + "\r\n" +
        " |\tenv_group_default: " + ENV_GROUP_DEFAULT;
  }
}
