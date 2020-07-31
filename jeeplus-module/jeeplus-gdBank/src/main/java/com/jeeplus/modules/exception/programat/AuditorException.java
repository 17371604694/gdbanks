package com.jeeplus.modules.exception.programat;

/**
 * 审核
 */
public class AuditorException extends Exception{

    public AuditorException() {
        super();
    }

    public AuditorException(String message) {
        super(message);
    }

    public AuditorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditorException(Throwable cause) {
        super(cause);
    }

    protected AuditorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
