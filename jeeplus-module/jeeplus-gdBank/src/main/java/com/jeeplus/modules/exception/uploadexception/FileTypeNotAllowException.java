package com.jeeplus.modules.exception.uploadexception;

public class FileTypeNotAllowException extends Exception{
    public FileTypeNotAllowException() {
    }

    public FileTypeNotAllowException(String message) {
        super(message);
    }

    public FileTypeNotAllowException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileTypeNotAllowException(Throwable cause) {
        super(cause);
    }

    public FileTypeNotAllowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
