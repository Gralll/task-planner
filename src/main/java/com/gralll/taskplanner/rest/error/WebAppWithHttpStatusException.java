package com.gralll.taskplanner.rest.error;

public class WebAppWithHttpStatusException extends RuntimeException {
    public WebAppWithHttpStatusException() {
    }

    public WebAppWithHttpStatusException(String message) {
        super(message);
    }

    public WebAppWithHttpStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebAppWithHttpStatusException(Throwable cause) {
        super(cause);
    }
}
