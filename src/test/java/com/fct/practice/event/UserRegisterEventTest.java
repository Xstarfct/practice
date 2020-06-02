package com.fct.practice.event;

import com.fct.practice.PracticeApplicationTests;
import com.fct.practice.modal.UserDO;
import com.fct.practice.utils.Sahara;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * UserRegisterEventTest
 *
 * @author xstarfct
 * @version 2020-05-21 4:56 下午
 */
@Slf4j
public class UserRegisterEventTest extends PracticeApplicationTests {
    
    @Test
    void publishRegisterListener() throws Exception {
        // 模拟注册逻辑
        UserDO fct = UserDO.builder().name("fct").age(10).gender(1).id(Sahara.instance.getSand()).isMember(Boolean.TRUE).build();
        log.info("尊敬的【{}】用户,欢迎您加入我们", fct.getName());
        
        //执行之后的逻辑
        applicationContext.publishEvent(new UserRegisterEvent(this, fct));
    
        TimeUnit.SECONDS.sleep(10);
    }
}
