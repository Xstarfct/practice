package com.fct.dubbo.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ProxyConfig.class})
@SpringBootApplication
public class DubboProxyApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboProxyApplication.class, args);
  }
}
