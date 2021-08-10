package com.fct.rpc.core.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fct
 * @date 2021-08-09 14:22
 */
@Data
@Accessors(chain = true)
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = -205068955841060434L;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 版本
     */
    private String version;

    /**
     * 地址
     */
    private String address;

    /**
     * 端口
     */
    private Integer port;
}
