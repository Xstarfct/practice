package com.fct.dubbo.config;

import com.fct.dubbo.api.service.UserService;
import java.rmi.RemoteException;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * RmiServerConfig
 *
 * @author xstarfct
 * @version 2020-09-10 11:54
 */
@Configuration
public class RmiServerConfig {
    
    @Resource
    private UserService userService;
    
    /**
     * springboot实现RMI
     */
    @Bean
    public RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("userService");
        rmiServiceExporter.setService(userService);
        rmiServiceExporter.setServiceInterface(UserService.class);
        // 默认为1099，注意占用问题
        rmiServiceExporter.setRegistryPort(10086);
        try {
            rmiServiceExporter.afterPropertiesSet();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return rmiServiceExporter;
    }
}
