package com.fct.dubbo.proxy.server;

import com.alibaba.fastjson.JSON;
import com.fct.dubbo.proxy.domain.ServiceDefinition;
import com.fct.dubbo.proxy.domain.ServiceMapping;
import com.fct.dubbo.proxy.metadata.MetadataCollector;
import com.fct.dubbo.proxy.router.EnvRouter;
import com.fct.dubbo.proxy.utils.Constants;
import com.fct.dubbo.proxy.utils.NamingThreadFactory;
import com.fct.dubbo.proxy.worker.RequestWorker;
import com.google.common.collect.ImmutableMap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ChannelHandler.Sharable
public class HttpProcessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private final ExecutorService businessThreadPool;
  private final MetadataCollector metadataCollector;
  private final ServiceMapping serviceMapping;

  public HttpProcessHandler(ServiceMapping serviceMapping, MetadataCollector metadataCollector) {
    super();
    this.businessThreadPool =
        Executors.newFixedThreadPool(
            serviceMapping.getThreadCount(), new NamingThreadFactory("Dubbo-proxy-request-worker"));
    this.metadataCollector = metadataCollector;
    this.serviceMapping = serviceMapping;
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
    // eg: http://localhost:8000/wmsprod/la.kaike.TempFacade?group={}&version={} + postData:{json}
    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(msg.uri());
    String path = queryStringDecoder.rawPath();
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    String postParam = msg.content().toString(CharsetUtil.UTF_8);
    if (!path.contains("/") || StringUtils.isBlank(postParam)) {
      doRequest(ctx, null, msg);
      return;
    }
    String application = path.split("/")[0];
    String service = path.split("/")[1];
    Map<String, List<String>> params = queryStringDecoder.parameters();
    if (params.containsKey(Constants.GROUP_KEY)) {
      service = params.get(Constants.GROUP_KEY).get(0) + "/" + service;
    }
    if (params.containsKey(Constants.VERSION_KEY)) {
      service = service + ":" + params.get(Constants.VERSION_KEY).get(0);
    }
    ServiceDefinition serviceDefinition =
        JSON.parseObject(postParam, ServiceDefinition.class)
            .setServiceID(service)
            .setApplication(application);
    if (CollectionUtils.isEmpty(serviceDefinition.getAttachments())
        && Constants.DEFAULT_ENV.equals(serviceMapping.getEnv())) {
      serviceDefinition.setAttachments(
          ImmutableMap.of(EnvRouter.DUBBO_ROUTER_GROUP, "stable")); /*使用默认的stable环境*/
    }
    doRequest(ctx, serviceDefinition, msg);
  }

  private void doRequest(
      ChannelHandlerContext ctx, ServiceDefinition serviceDefinition, HttpRequest msg) {
    businessThreadPool.execute(new RequestWorker(serviceDefinition, ctx, msg, metadataCollector));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
