package com.gralll.taskplanner.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public final class ResponseUtil {

    private ResponseUtil() {

    }

    public static <R> ResponseEntity<R> okOrNotFound(Optional<?> checkedObject, R addedContent) {
        return checkedObject.map(existedObject ->
                ResponseEntity.ok().body(addedContent))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
