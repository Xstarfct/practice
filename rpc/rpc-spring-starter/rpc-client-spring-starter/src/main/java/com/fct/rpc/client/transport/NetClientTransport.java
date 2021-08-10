package com.fct.rpc.client.transport;

import com.fct.rpc.core.common.RpcResponse;
import com.fct.rpc.core.protocol.MessageProtocol;

/**
 * 网络传输层
 */
public interface NetClientTransport {

    /**
     *  发送数据
     * @param metadata
     * @return
     * @throws Exception
     */
    MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception;

}
