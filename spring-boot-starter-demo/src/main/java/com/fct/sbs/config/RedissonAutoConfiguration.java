package com.fct.sbs.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * RedissonAutoConfiguration
 *
 * @author fct
 * @version 2021-06-30 16:04
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

  @Bean
  RedissonClient redissonClient(RedissonProperties redissonProperties) {
    Config config = new Config();
    String prefix = "redis://";
    if (redissonProperties.isSsl()) {
      prefix = "rediss://";
    }
    SingleServerConfig singleServerConfig =
        config
            .useSingleServer()
            .setAddress(prefix + redissonProperties.getHost() + ":" + redissonProperties.getPort())
            .setConnectTimeout(redissonProperties.getTimeout());
    if (!StringUtils.hasText(redissonProperties.getPassword())) {
      singleServerConfig.setPassword(redissonProperties.getPassword());
    }
    return Redisson.create(config);
  }
}
