package com.fct.dubbo.proxy.worker;

import com.alibaba.fastjson.JSON;

import com.fct.dubbo.proxy.domain.ApiProxyResult;
import com.fct.dubbo.proxy.domain.BaseResponseCode;
import com.fct.dubbo.proxy.domain.ServiceDefinition;
import com.fct.dubbo.proxy.metadata.MetadataCollector;
import com.fct.dubbo.proxy.service.GenericInvoke;
import com.fct.dubbo.proxy.utils.Constants;
import com.fct.dubbo.proxy.utils.Tool;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;

import java.util.List;
import java.util.Set;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class RequestWorker implements Runnable {

  private final ServiceDefinition serviceDefinition;
  private final ChannelHandlerContext ctx;
  private final HttpRequest msg;
  private final MetadataCollector metadataCollector;

  public RequestWorker(
      ServiceDefinition serviceDefinition,
      ChannelHandlerContext ctx,
      HttpRequest msg,
      MetadataCollector metadataCollector) {
    this.serviceDefinition = serviceDefinition;
    this.ctx = ctx;
    this.msg = msg;
    this.metadataCollector = metadataCollector;
  }

  @Override
  public void run() {
    Object result;
    try {
      if (serviceDefinition == null) {
        result = ApiProxyResult.fail(BaseResponseCode.FATAL_ERROR);
      } else {
        String serviceID = serviceDefinition.getServiceID();
        String interfaze = Tool.getInterface(serviceID);
        String group = Tool.getGroup(serviceID);
        String version = Tool.getVersion(serviceID);
        if (serviceDefinition.getParamTypes() == null
            && serviceDefinition.getParamValues() != null) {
          String[] types =
              getTypesFromMetadata(
                  serviceDefinition.getApplication(),
                  interfaze,
                  group,
                  version,
                  serviceDefinition.getMethodName(),
                  serviceDefinition.getParamValues().length);
          serviceDefinition.setParamTypes(types);
        }
        result = GenericInvoke.genericCall(interfaze, group, version, serviceDefinition);
      }
    } catch (Exception e) {
      // e.printStackTrace();
      result = ApiProxyResult.fail(BaseResponseCode.SYSTEM_ERROR);
    }
    if (!writeResponse(ctx, result)) {
      ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
  }

  private boolean writeResponse(ChannelHandlerContext ctx, Object result) {
    // Decide whether to close the connection or not.
    // Build the response object.
    boolean keepAlive = HttpUtil.isKeepAlive(this.msg);
    FullHttpResponse response =
        new DefaultFullHttpResponse(
            HTTP_1_1,
            OK,
            Unpooled.copiedBuffer(
                JSON.toJSONString(ApiProxyResult.markResult(result)), CharsetUtil.UTF_8));

    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

    if (keepAlive) {
      // Add 'Content-Length' header only for a keep-alive connection.
      response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
      // Add keep alive header as per:
      // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
      response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    }

    //         Encode the cookie.
    String cookieString = msg.headers().get(HttpHeaderNames.COOKIE);
    if (cookieString != null) {
      Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
      if (!cookies.isEmpty()) {
        // Reset the cookies if necessary.
        for (Cookie cookie : cookies) {
          response
              .headers()
              .add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
        }
      }
    }

    // Write the response.
    ctx.writeAndFlush(response);

    return keepAlive;
  }

  private String[] getTypesFromMetadata(
      String application,
      String interfaze,
      String group,
      String version,
      String methodName,
      int paramLen) {
    MetadataIdentifier identifier =
        new MetadataIdentifier(interfaze, version, group, Constants.PROVIDER_SIDE, application);
    String metadata = metadataCollector.getProviderMetaData(identifier);
    // 一些老的应用没有元数据无法获取类型
    if (StringUtils.isBlank(metadata)) {
      return null;
    }
    FullServiceDefinition serviceDefinition =
        JSON.parseObject(metadata, FullServiceDefinition.class);
    List<MethodDefinition> methods = serviceDefinition.getMethods();
    if (methods != null) {
      for (MethodDefinition m : methods) {
        if (Tool.sameMethod(m, methodName, paramLen)) {
          return m.getParameterTypes();
        }
      }
    }
    return null;
  }
}
