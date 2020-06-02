package com.fct.practice.event;

import com.fct.practice.modal.UserDO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 在Spring内部中有多种方式实现监听如：@EventListener注解、实现ApplicationListener泛型接口、实现SmartApplicationListener接口等
 *
 * @author xstarfct
 * @version 2020-05-21 4:37 下午
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = -346931273227854880L;
    
    public UserRegisterEvent(Object source, UserDO user) {
        super(source);
        this.user = user;
    }
    
    private final UserDO user;
}
