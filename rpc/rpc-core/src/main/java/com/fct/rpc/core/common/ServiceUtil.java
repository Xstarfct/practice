package com.fct.rpc.core.common;

/**
 * @author fct
 * @date 2021-08-09 14:22
 */
public class ServiceUtil {

    /**
     * serviceKey
     * @param serviceName 服务名称
     * @param version 版本
     * @return service
     */
    public static String serviceKey(String serviceName, String version) {
        return String.join("-", serviceName, version);
    }

}
