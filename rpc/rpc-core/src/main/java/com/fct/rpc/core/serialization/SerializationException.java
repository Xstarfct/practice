package com.fct.rpc.core.serialization;

/**
 * @author fct
 * @date 2021-08-09 14:23
 */
public class SerializationException extends RuntimeException {

    private static final long serialVersionUID = -1451573691052714540L;

    public SerializationException() {
        super();
    }

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
