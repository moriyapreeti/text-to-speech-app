package com.tts.app.exception;

import com.tts.app.dto.ErrorResponse;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        String message =
                exception
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .findFirst()
                        .map(error -> error.getDefaultMessage())
                        .orElse("Invalid request.");


        ErrorResponse response =
                new ErrorResponse(
                        message,
                        HttpStatus.BAD_REQUEST.value()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(
            ConstraintViolationException.class
    )
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    
    @ExceptionHandler(
            InvalidTtsRequestException.class
    )
    public ResponseEntity<ErrorResponse> handleInvalidTtsRequest(
            InvalidTtsRequestException exception
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        exception.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    @ExceptionHandler(TtsException.class)
    public ResponseEntity<ErrorResponse> handleTtsException(
            TtsException exception
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        exception.getMessage(),
                        HttpStatus.BAD_GATEWAY.value()
                );


        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception exception
    ) {

        ErrorResponse response =
                new ErrorResponse(
                        "Something went wrong on the server.",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                );


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
