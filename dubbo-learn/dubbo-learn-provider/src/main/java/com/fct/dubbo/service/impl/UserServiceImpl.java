package com.fct.dubbo.service.impl;

import com.fct.dubbo.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * UserServiceImpl
 *
 * @author xstarfct
 * @version 2020-07-03 5:58 下午
 */
@DubboService
public class UserServiceImpl implements UserService {
    
    @Override
    public String test(String name) {
        return name;
    }
}
