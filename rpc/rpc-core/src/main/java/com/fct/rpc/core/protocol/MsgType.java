package com.fct.rpc.core.protocol;

import lombok.Getter;

/**
 * @author fct
 * @date 2021-08-09 14:23
 */
public enum MsgType {
    REQUEST((byte) 1),
    RESPONSE((byte) 2);

    @Getter
    private byte type;

    MsgType(byte type) {
        this.type = type;
    }

    public static MsgType findByType(byte type) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }
}
