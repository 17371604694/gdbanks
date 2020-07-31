package com.jeeplus.modules.exception.uploadexception;

public class SizeBeyondException extends Exception{
    public SizeBeyondException() {
    }

    public SizeBeyondException(String message) {
        super(message);
    }

    public SizeBeyondException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeBeyondException(Throwable cause) {
        super(cause);
    }

    public SizeBeyondException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
