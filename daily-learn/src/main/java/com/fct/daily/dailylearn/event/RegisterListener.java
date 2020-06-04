package com.fct.daily.dailylearn.event;

import com.alibaba.fastjson.JSON;
import com.fct.daily.dailylearn.modal.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * RegisterListener
 *
 * @author xstarfct
 * @version 2020-05-21 4:40 下午
 */
@Component
@Slf4j
public class RegisterListener implements ApplicationListener<UserRegisterEvent> {
    
    @Async
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        //获取注册用户对象
        UserDO user = event.getUser();
        
        //../省略逻辑
        /****注册完你就可以变成会员了***/
        user.setIsMember(Boolean.TRUE);
        
        log.info("resource = {}", JSON.toJSONString(event.getSource()));
        
        //输出注册用户信息
        log.info("恭喜你: {}, 已经变成会员了, user={}", user.getName(), JSON.toJSONString(user));
    }
}
