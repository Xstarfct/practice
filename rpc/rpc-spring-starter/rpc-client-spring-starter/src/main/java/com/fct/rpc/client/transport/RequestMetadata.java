package com.fct.rpc.client.transport;

import com.fct.rpc.core.common.RpcRequest;
import com.fct.rpc.core.protocol.MessageProtocol;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求元数据
 */
@Data
@Builder
public class RequestMetadata implements Serializable {

    private static final long serialVersionUID = -1758309043304880122L;
    /**
     * 协议
     */
    private MessageProtocol<RpcRequest> protocol;

    /**
     * 地址
     */
    private String address;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 服务调用超时
     */
    private Integer timeout;

}
