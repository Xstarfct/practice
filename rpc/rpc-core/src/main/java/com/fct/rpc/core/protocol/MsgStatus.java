package com.fct.rpc.core.protocol;

import lombok.Getter;

/**
 * 请求状态
 * 
 * @author fct
 * @date 2021-08-09 14:23
 */
public enum MsgStatus {
    SUCCESS((byte)0), FAIL((byte)1);

    @Getter
    private final byte code;

    MsgStatus(byte code) {
        this.code = code;
    }

    public static boolean isSuccess(byte code) {
        return MsgStatus.SUCCESS.code == code;
    }

}
