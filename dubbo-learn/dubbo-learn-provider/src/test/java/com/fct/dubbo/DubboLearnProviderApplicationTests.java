package com.fct.dubbo;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DubboLearnProviderApplicationTests {
    
    @Resource
    protected ApplicationContext applicationContext;
    
    @Test
    public void contextLoads() {
        Object service = applicationContext.getBean("helloService");
        System.out.println("haha");
        System.out.println(service);
    }
    
}
