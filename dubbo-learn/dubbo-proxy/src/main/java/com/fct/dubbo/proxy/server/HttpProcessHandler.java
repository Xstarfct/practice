package com.fct.dubbo.proxy.server;

import com.alibaba.fastjson.JSON;
import com.fct.dubbo.proxy.dao.ServiceDefinition;
import com.fct.dubbo.proxy.dao.ServiceMapping;
import com.fct.dubbo.proxy.metadata.MetadataCollector;
import com.fct.dubbo.proxy.utils.NamingThreadFactory;
import com.fct.dubbo.proxy.worker.RequestWorker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class HttpProcessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private final ExecutorService businessThreadPool;
  private final MetadataCollector metadataCollector;
  private final ServiceMapping serviceMapping;

  public HttpProcessHandler(
      int businessThreadCount, ServiceMapping serviceMapping, MetadataCollector metadataCollector) {
    super();
    this.businessThreadPool =
        Executors.newFixedThreadPool(
            businessThreadCount, new NamingThreadFactory("Dubbo-proxy-request-worker"));
    this.metadataCollector = metadataCollector;
    this.serviceMapping = serviceMapping;
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {

    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(msg.uri());
    String path = queryStringDecoder.rawPath();
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    if (path.contains("/")) {
      String application = path.split("/")[0];
      String service = path.split("/")[1];
      Map<String, List<String>> params = queryStringDecoder.parameters();
      if (params.containsKey("group")) {
        service = params.get("group").get(0) + "/" + service;
      }
      if (params.containsKey("version")) {
        service = service + ":" + params.get("version").get(0);
      }
      ByteBuf raw = msg.content();
      String info = raw.toString(CharsetUtil.UTF_8);
      ServiceDefinition serviceDefinition = JSON.parseObject(info, ServiceDefinition.class);
      serviceDefinition.setServiceID(service);
      serviceDefinition.setApplication(application);
      doRequest(ctx, serviceDefinition, msg);
    } else {
      // TODO error handle
    }
  }

  private void doRequest(
      ChannelHandlerContext ctx, ServiceDefinition serviceDefinition, HttpRequest msg) {
    businessThreadPool.execute(
        new RequestWorker(serviceDefinition, ctx, msg, metadataCollector, serviceMapping));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
