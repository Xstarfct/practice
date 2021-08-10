package com.fct.rpc.core.discovery;

import com.fct.rpc.core.common.ServiceInfo;

/**
 * DiscoveryService
 * 
 * @author fct
 * @date 2021-08-09 14:23
 */
public interface DiscoveryService {

    /**
     * 发现
     * 
     * @param serviceName
     * @return
     * @throws Exception
     */
    ServiceInfo discovery(String serviceName) throws Exception;

}
