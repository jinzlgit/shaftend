package com.jyd.core.exception;

/**
 * @author Zhenlin Jin
 * @date 2021/8/13 9:08
 */
public class LengthNotEnoughException extends RuntimeException {
    public LengthNotEnoughException(String message) {
        super(message);
    }

    public LengthNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public LengthNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected LengthNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
