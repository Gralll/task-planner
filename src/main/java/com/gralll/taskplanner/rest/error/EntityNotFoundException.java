package com.gralll.taskplanner.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends WebAppWithHttpStatusException {

    private static final String ERROR_TEMPLATE = "%s with id [%s] not found!";

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Long entityId) {
        super(String.format(ERROR_TEMPLATE, StringUtils.capitalize(entityName), entityId.toString()));
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
