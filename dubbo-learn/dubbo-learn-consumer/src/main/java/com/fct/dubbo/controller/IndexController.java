package com.fct.dubbo.controller;

import com.fct.dubbo.api.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 *
 * @author xstarfct
 * @version 2020-09-09 15:33
 */
@RestController
@RequestMapping("index")
@Slf4j
public class IndexController {
    
    @DubboReference(group = "hello1")
    private HelloService helloService1;
    @DubboReference(group = "hello2")
    private HelloService helloService2;
    
    @RequestMapping("/hello")
    public String hello() {
        return helloService1.sayHello("1") + "  ---- " + helloService2.sayHello("2");
    }
}
