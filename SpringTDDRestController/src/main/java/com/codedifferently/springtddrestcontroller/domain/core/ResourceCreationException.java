package com.codedifferently.springtddrestcontroller.domain.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceCreationException extends RuntimeException{

    public ResourceCreationException(String message) {
        super(message);
    }
}
