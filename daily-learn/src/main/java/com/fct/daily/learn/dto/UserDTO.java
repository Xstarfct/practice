package com.fct.daily.learn.dto;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * UserDTO
 *
 * @author xstarfct
 * @version 2020-05-21 5:37 下午
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UserDTO implements Serializable {
    
    private static final long serialVersionUID = 55990545976732190L;
    private Long id;
    private String name;
    private Integer age;
    private Integer gender;
    @JSONField(name = "is_member")
    private Boolean isMember;
    
}
