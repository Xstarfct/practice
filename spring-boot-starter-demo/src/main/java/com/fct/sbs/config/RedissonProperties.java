package com.fct.sbs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RedissonProperties
 *
 * @author fct
 * @version 2021-06-30 15:50
 */
@ConfigurationProperties(prefix = "fct.redisson")
@Setter
@Getter
public class RedissonProperties {
  private String host = "localhost";
  private String password;
  private int port = 6379;
  private int timeout;
  private boolean ssl;
}
