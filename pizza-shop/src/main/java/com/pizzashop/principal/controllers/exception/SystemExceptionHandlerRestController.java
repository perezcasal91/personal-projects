package com.pizzashop.principal.controllers.exception;

import com.pizzashop.principal.dtos.ExceptionResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.lang.IllegalArgumentException;

@RestControllerAdvice
@Slf4j
public class SystemExceptionHandlerRestController {

    private static ExceptionResponseDTO getExceptionDTO(
            HttpStatus status, String exception) {
        return new ExceptionResponseDTO(
                status.value(),
                exception,
                LocalDate.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> illegalArgumentExceptionHandler
            (IllegalArgumentException exception) {
        log.info("Executing illegalArgumentExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> malformedJwtExceptionHandler
            (MalformedJwtException exception) {
        log.info("Executing malformedJwtExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, "This is not a valid token.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signatureExceptionHandler
            (SignatureException exception) {
        log.info("Executing signatureExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, "Authentication Failed. Token not valid.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> noSuchElementExceptionHandler
            (NoSuchElementException exception) {
        log.info("Executing noSuchElementExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> authenticationExceptionHandler
            (AuthenticationException exception) {
        log.info("Executing authenticationExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.UNAUTHORIZED, exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponseDTO);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> usernameNotFoundExceptionHandler
            (UsernameNotFoundException exception) {
        log.info("Executing usernameNotFoundExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.NOT_FOUND, exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler
            (MethodArgumentNotValidException exception) {
        log.info("Executing methodArgumentNotValidExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST,
                exception.getBody().getDetail() + " In field: "
                        + Objects.requireNonNull(exception.getFieldError()).getField() + ".");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> nullPointerExceptionHandler
            (NullPointerException exception) {
        log.info("Executing nullPointerExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> expiredJwtExceptionHandler
            (ExpiredJwtException exception) {
        log.info("Executing expiredJwtExceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> exceptionHandler
            (Exception exception) {
        log.info("Executing exceptionHandler from " +
                "SystemExceptionHandlerRestController");

        ExceptionResponseDTO exceptionResponseDTO = getExceptionDTO(
                HttpStatus.BAD_REQUEST, exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }
}
