package com.codedifferently.watertrackerapi.domain.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceUpdateException extends RuntimeException{
    public ResourceUpdateException(String message) {
        super(message);
    }
}
