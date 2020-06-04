package com.fct.daily.dailylearn.modal;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * UserDO
 *
 * @author xstarfct
 * @version 2020-05-18 5:14 下午
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDO implements Serializable {
    
    private static final long serialVersionUID = -6323534741728246709L;
    private Long id;
    private String name;
    private Integer age;
    private Integer gender;
    private Boolean isMember;
}
