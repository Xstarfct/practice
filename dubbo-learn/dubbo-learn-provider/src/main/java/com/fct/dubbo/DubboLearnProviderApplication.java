package com.fct.dubbo;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "dubbo-learn-provider.yml", autoRefreshed = true)
public class DubboLearnProviderApplication {
    
    /****
     * 不需要web的启动方式
     * new SpringApplicationBuilder(DubboLearnProviderApplication.class).web(WebApplicationType.NONE).run(args);
     **/
    public static void main(String[] args) {
        SpringApplication.run(DubboLearnProviderApplication.class, args);
    }
    
}
