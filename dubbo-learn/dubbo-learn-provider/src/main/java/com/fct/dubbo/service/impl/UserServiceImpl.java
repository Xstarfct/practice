package com.fct.dubbo.service.impl;

import com.fct.dubbo.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author xstarfct
 * @version 2020-07-03 5:58 下午
 */
@Service("userService")
@DubboService
public class UserServiceImpl implements UserService {
    
    @Override
    public String test(String name) {
        return name;
    }
}
