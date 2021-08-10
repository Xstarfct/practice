package com.fct.rpc.core.serialization;

import java.io.IOException;

/**
 * RpcSerialization
 * 
 * @author fct
 * @date 2021-08-09 14:23
 */
public interface RpcSerialization {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}