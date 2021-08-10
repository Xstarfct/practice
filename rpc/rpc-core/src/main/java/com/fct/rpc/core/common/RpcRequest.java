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
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 4710786378039305613L;
    /**
     * 请求的服务名 + 版本
     */
    private String serviceName;
    /**
     * 请求调用的方法
     */
    private String method;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数
     */
    private Object[] parameters;

}
