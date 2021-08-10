package com.fct.rpc.core.exception;

/**
 * @author fct
 * @date 2021-08-09 14:27
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7956052390855849828L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
