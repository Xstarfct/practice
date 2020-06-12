package com.fct.dubbo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DubboLearnProviderApplication {
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboLearnProviderApplication.class).web(WebApplicationType.NONE).run(args);
    }
    
}
