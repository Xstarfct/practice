package com.fct.rpc.core.exception;

/**
 * @author fct
 * @date 2021-08-09 14:27
 */
public class RpcException extends RuntimeException {

    private static final long serialVersionUID = -4691295025498785455L;

    public RpcException() {
        super();
    }

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
