package com.fct.dubbo.proxy;

import com.fct.dubbo.proxy.domain.ServiceMapping;
import com.fct.dubbo.proxy.metadata.MetadataCollector;
import com.fct.dubbo.proxy.utils.Constants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@ConfigurationProperties(prefix = "proxy")
@Configuration
@Getter
@Setter
public class ProxyConfig {

  private String registryAddress;

  private String group;

  private String metadataAddress;

  private Integer threadCount;

  /** 网关环境 dev、test、pre、pro */
  private String env;

  private List<Mapping> services;

  @Bean
  public ServiceMapping getServiceMapping() {
    ServiceMapping serviceMapping = new ServiceMapping();
    serviceMapping.setMappings(services);
    serviceMapping.setEnv(StringUtils.isBlank(env) ? Constants.DEFAULT_ENV : env);
    serviceMapping.setThreadCount(threadCount);
    return serviceMapping;
  }

  @Bean
  Registry getRegistry() {
    URL url = URL.valueOf(registryAddress);
    if (StringUtils.isNotEmpty(group)) {
      url = url.addParameter(CommonConstants.GROUP_KEY, group);
    }
    RegistryFactory registryFactory =
        ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
    return registryFactory.getRegistry(url);
  }

  @Bean
  MetadataCollector getMetadataCollector() {
    MetadataCollector metaDataCollector = null;
    if (StringUtils.isNotEmpty(metadataAddress)) {
      metaDataCollector =
          ExtensionLoader.getExtensionLoader(MetadataCollector.class)
              .getExtension(URL.valueOf(metadataAddress).getProtocol());
    }
    return metaDataCollector;
  }

  @Data
  public static class Mapping {
    private String name;
    private String interfaze;
    private String group;
    private String version;
  }
}
