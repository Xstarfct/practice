package com.fct.practice.service.convert;

import com.fct.practice.dto.UserDTO;
import com.fct.practice.modal.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserConvert
 * eg：https://blog.csdn.net/zhige_me/article/details/80699784
 * @author xstarfct
 * @version 2020-05-21 5:39 下午
 */
@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    
    UserDTO domain2Dto(UserDO userDO);
}
