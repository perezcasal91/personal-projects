package com.pizzashop.principal.controllers.exception;


import com.pizzashop.principal.dtos.ExceptionResponseDTO;
import com.pizzashop.principal.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandlerRestController {


    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> usernameAlreadyExistsExceptionHandler
            (UsernameAlreadyExistsException exception) {
        log.info("Executing usernameAlreadyExistsExceptionHandler from" +
                " CustomExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDate.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> emailAlreadyExistsExceptionHandler
            (EmailAlreadyExistsException exception) {
        log.info("Executing emailAlreadyExistsExceptionHandler from" +
                " CustomExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDate.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
    }

    @ExceptionHandler(NameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> nameAlreadyExistsExceptionHandler
            (NameAlreadyExistsException exception) {
        log.info("Executing nameAlreadyExistsExceptionHandler from " +
                "CustomExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDate.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
    }

    @ExceptionHandler(IncompatibleAuthoritiesException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> authoritiesExceptionHandler
            (IncompatibleAuthoritiesException exception) {
        log.info("Executing authoritiesExceptionHandler from " +
                "CustomExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage(),
                LocalDate.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponseDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> entityNotFoundExceptionHandler
            (EntityNotFoundException exception) {
        log.info("Executing entityNotFoundExceptionHandler from " +
                "CustomExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDate.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);
    }

}
