package com.pizzashop.principal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NameAlreadyExistsException extends ResponseStatusException {

    /**
     * Exception: Name Already Exists
     *
     * @param message String
     */
    public NameAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
