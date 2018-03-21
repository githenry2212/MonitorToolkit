package com.panther.toolkit.exception;

/**
 * @author yangfan
 * @since 2018/3/21 17:06
 */
public class MonitorException extends Exception {
    public MonitorException(String message) {
        super(message);
    }

    public MonitorException(String message, Throwable cause) {
        super(message, cause);
    }
}
