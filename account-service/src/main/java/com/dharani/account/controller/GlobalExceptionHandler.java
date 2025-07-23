package com.dharani.account.controller;

import com.dharani.account.exceptions.ErrorResponse;
import com.dharani.account.exceptions.RecordNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundException(RuntimeException ex, HttpServletRequest request) {

        System.out.println("Global exception handler invoked");
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                                                        ex.getMessage(),
                                                        HttpStatus.NOT_FOUND.value(),
                                                        request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
