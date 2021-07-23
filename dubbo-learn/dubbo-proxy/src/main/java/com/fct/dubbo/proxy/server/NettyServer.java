package com.fct.dubbo.proxy.server;

import com.fct.dubbo.proxy.dao.ServiceMapping;
import com.fct.dubbo.proxy.metadata.MetadataCollector;
import com.fct.dubbo.proxy.service.GenericInvoke;
import com.fct.dubbo.proxy.utils.InetAddressUtil;
import com.fct.dubbo.proxy.utils.NamingThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.dubbo.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NettyServer {

  private final Logger logger = LoggerFactory.getLogger(NettyServer.class);
  private ServerBootstrap bootstrap;
  private EventLoopGroup bossGroup;
  private EventLoopGroup workerGroup;
  private final ExecutorService serverStart =
      Executors.newSingleThreadExecutor(new NamingThreadFactory("Dubbo-proxy-starter"));

  @Value("${netty.port}")
  private int port;

  @Value("${business.thread.count}")
  private int businessThreadCount;

  private final MetadataCollector metadataCollector;

  private final ServiceMapping serviceMapping;

  private final Registry registry;

  public NettyServer(
      MetadataCollector metadataCollector, ServiceMapping serviceMapping, Registry registry) {
    this.metadataCollector = metadataCollector;
    this.serviceMapping = serviceMapping;
    this.registry = registry;
  }

  @PostConstruct
  public void start() {
    serverStart.execute(
        () -> {
          init();
          String inetHost = InetAddressUtil.getLocalIP();
          try {
            ChannelFuture f = bootstrap.bind(inetHost, port).sync();
            logger.info("Dubbo proxy started, host is {} , port is {}.", inetHost, port);
            f.channel().closeFuture().sync();
            logger.info("Dubbo proxy closed, host is {} , port is {}.", inetHost, port);
          } catch (InterruptedException e) {
            logger.error("dubbo proxy start failed", e);
          } finally {
            destroy();
          }
        });
  }

  private void init() {
    GenericInvoke.setRegistry(this.registry);
    bootstrap = new ServerBootstrap();
    bossGroup =
        new NioEventLoopGroup(
            Runtime.getRuntime().availableProcessors(),
            new NamingThreadFactory("" + "Dubbo-Proxy-Boss"));
    workerGroup =
        new NioEventLoopGroup(
            Runtime.getRuntime().availableProcessors() * 2,
            new NamingThreadFactory("Dubbo-Proxy-Work"));
    HttpProcessHandler processHandler =
        new HttpProcessHandler(businessThreadCount, serviceMapping, metadataCollector);
    bootstrap
        .group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ProxyChannelInitializer(processHandler))
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childOption(ChannelOption.SO_KEEPALIVE, true);
  }

  @PreDestroy
  public void destroy() {
    if (workerGroup != null) {
      workerGroup.shutdownGracefully();
    }
    if (bossGroup != null) {
      bossGroup.shutdownGracefully();
    }
    serverStart.shutdown();
  }

  private static class ProxyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final HttpProcessHandler httpProcessHandler;

    public ProxyChannelInitializer(HttpProcessHandler httpProcessHandler) {
      this.httpProcessHandler = httpProcessHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {

      ch.pipeline()
          .addLast(
              new LoggingHandler(NettyServer.class, LogLevel.DEBUG),
              new HttpServerCodec(),
              new HttpObjectAggregator(512 * 1024 * 1024),
              httpProcessHandler);
    }
  }
}
