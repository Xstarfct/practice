package com.fct.rpc.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 协议消息体
 * 
 * @author fct
 * @date 2021-08-09 14:23
 */
@Data
public class MessageProtocol<T> implements Serializable {

    private static final long serialVersionUID = -2198437239543503046L;
    /**
     * 消息头
     */
    private MessageHeader header;

    /**
     * 消息体
     */
    private T body;

}
