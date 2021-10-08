package com.fct.rpc.core.balancer.impl;

import com.fct.rpc.core.balancer.LoadBalance;
import com.fct.rpc.core.common.ServiceInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询算法
 */
public class FullRoundBalance implements LoadBalance {

    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public ServiceInfo chooseOne(List<ServiceInfo> services) {
        // 防止并发情况下，count超出services.size()
        if (count.get() >= services.size()) {
            count.set(0);
        }
        return services.get(count.getAndAdd(1));
    }
}
