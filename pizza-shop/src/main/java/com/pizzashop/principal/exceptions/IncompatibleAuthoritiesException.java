package com.pizzashop.principal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncompatibleAuthoritiesException extends ResponseStatusException {

    /**
     * Exception: Authorities
     *
     * @param message String
     */
    public IncompatibleAuthoritiesException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
