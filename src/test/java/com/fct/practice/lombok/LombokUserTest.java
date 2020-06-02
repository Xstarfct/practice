package com.fct.practice.lombok;

import com.alibaba.fastjson.JSON;
import com.fct.practice.dto.UserDTO;
import com.fct.practice.modal.UserDO;
import com.fct.practice.service.convert.UserConvert;
import com.fct.practice.utils.Sahara;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * LombokUserTest
 *
 * @author xstarfct
 * @version 2020-05-18 5:17 下午
 */
@Slf4j
public class LombokUserTest {
    
    
    @Test
    public void name() {
        UserDO fct = UserDO.builder().name("fct").age(10).gender(1).id(Sahara.instance.getSand()).isMember(Boolean.TRUE).build();
        
        UserDO fct2 = new UserDO().setAge(10).setName("Xstarfct").setGender(1).setId(Sahara.instance.getSand()).setIsMember(Boolean.FALSE);
        
        log.info("fct = {}, fct2 = {}", JSON.toJSONString(fct), JSON.toJSONString(fct2));
        
        // 下划线的is_member
        UserDO user = JSON.parseObject("{\"age\":10,\"gender\":1,\"id\":3842280579672309760,\"is_member\":false,\"name\":\"Xstarfct\"}", UserDO.class);
        log.info("user={}", user);
        // 驼峰的isMember
        user = JSON.parseObject("{\"age\":10,\"gender\":1,\"id\":3842280579672309760,\"isMember\":false,\"name\":\"Xstarfct\"}", UserDO.class);
        log.info("user={}", user);
    }
    
    @Test
    public void testMapper() {
        UserDO fct2 = new UserDO().setAge(10).setName("Xstarfct").setGender(1).setId(Sahara.instance.getSand()).setIsMember(Boolean.FALSE);
        UserDTO dto = UserConvert.INSTANCE.domain2Dto(fct2);
        
        log.info("dto={}, dtoJson={}", dto, JSON.toJSONString(dto));
    }
}
