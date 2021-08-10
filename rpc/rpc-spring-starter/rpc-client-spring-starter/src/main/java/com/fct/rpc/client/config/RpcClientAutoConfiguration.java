package com.fct.rpc.client.config;

import com.fct.rpc.client.processor.RpcClientProcessor;
import com.fct.rpc.client.proxy.ClientStubProxyFactory;
import com.fct.rpc.core.balancer.LoadBalance;
import com.fct.rpc.core.balancer.impl.FullRoundBalance;
import com.fct.rpc.core.balancer.impl.RandomBalance;
import com.fct.rpc.core.discovery.DiscoveryService;
import com.fct.rpc.core.discovery.impl.ZookeeperDiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
public class RpcClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ClientStubProxyFactory clientStubProxyFactory() {
        return new ClientStubProxyFactory();
    }

//    @Primary
//    @Bean(name = "loadBalance")
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "rpc.client", name = "balance", havingValue = "randomBalance", matchIfMissing = true)
//    public LoadBalance randomBalance() {
//        return new RandomBalance();
//    }
//
//    @Bean(name = "loadBalance")
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "rpc.client", name = "balance", havingValue = "fullRoundBalance")
//    public LoadBalance loadBalance() {
//        return new FullRoundBalance();
//    }

    @Bean
    @ConditionalOnMissingBean
//    @ConditionalOnBean({LoadBalance.class})
    public DiscoveryService discoveryService(@Autowired RpcClientProperties rpcClientProperties/*,
                                             LoadBalance loadBalance*/) {
        System.out.println("\n" + rpcClientProperties + "\n");
        return new ZookeeperDiscoveryService(rpcClientProperties.getDiscoveryAddress(), new FullRoundBalance());
    }

    @Bean
    @ConditionalOnMissingBean
    static RpcClientProcessor rpcClientProcessor(ClientStubProxyFactory clientStubProxyFactory,
        DiscoveryService discoveryService, @Autowired RpcClientProperties rpcClientProperties) {
        return new RpcClientProcessor(clientStubProxyFactory, discoveryService, rpcClientProperties);
    }

}
