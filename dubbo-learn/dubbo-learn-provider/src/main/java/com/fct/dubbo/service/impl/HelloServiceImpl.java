package com.fct.dubbo.service.impl;

import com.fct.dubbo.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * HelloServiceImpl
 *
 * @author xstarfct
 * @version 2020-06-04 2:02 下午
 */
@Service("helloService")
@DubboService(group = "hello1")
public class HelloServiceImpl implements HelloService {
    
    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;
    
    public String sayHello(String name) {
        return String.format("[%s] : Hello1, %s", serviceName, name);
    }
}
