package com.fct.dubbo.config;

import com.fct.dubbo.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * RmiConfig
 *
 * @author xstarfct
 * @version 2020-09-10 11:52
 */
@Configuration
public class RmiClientConfig {
    
    @Bean
    public RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://127.0.0.1:10086/userService");
        rmiProxyFactoryBean.setServiceInterface(UserService.class);
        return rmiProxyFactoryBean;
    }
}
