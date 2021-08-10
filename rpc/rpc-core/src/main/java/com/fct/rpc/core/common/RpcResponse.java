package com.fct.rpc.core.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fct
 * @date 2021-08-09 14:22
 */
@Data
@Accessors(chain = true)
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 8312556910285512601L;
    private Object data;
    private String message;

}
