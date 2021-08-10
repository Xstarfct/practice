package com.fct.rpc.core.balancer.impl;

import com.fct.rpc.core.balancer.LoadBalance;
import com.fct.rpc.core.common.ServiceInfo;

import java.util.List;
import java.util.Random;

/**
 * 随机算法
 * 
 * @author fct
 * @date 2021-08-09 15:29
 */
public class RandomBalance implements LoadBalance {

    private static final Random random = new Random();

    @Override
    public ServiceInfo chooseOne(List<ServiceInfo> services) {
        return services.get(random.nextInt(services.size()));
    }
}
