package com.capgemini.middleware.adapters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCertTypeException extends RuntimeException {
    public InvalidCertTypeException(Exception exception, String massage) {
        super(massage, exception);
    }
}
