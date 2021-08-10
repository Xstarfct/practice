package com.fct.rpc.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "rpc.client", ignoreInvalidFields = true)
public class RpcClientProperties implements Serializable {

    private static final long serialVersionUID = 7734132614516666206L;
    /**
     *  负载均衡
     */
    private String balance;

    /**
     *  序列化
     */
    private String serialization;

    /**
     *  服务发现地址
     */
    private String discoveryAddress;

    /**
     *  服务调用超时
     */
    private Integer timeout;

}
