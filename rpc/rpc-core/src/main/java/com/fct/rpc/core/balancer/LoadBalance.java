package com.fct.rpc.core.balancer;

import com.fct.rpc.core.common.ServiceInfo;

import java.util.List;

/**
 * 负载均衡算法接口
 * @author fct
 * @date 2021-08-09 14:29
 */
public interface LoadBalance {
    /**
     * 负载均衡选择算法
     * 
     * @param services
     *            服务列表
     * @return 某一个服务
     */
    ServiceInfo chooseOne(List<ServiceInfo> services);
}
