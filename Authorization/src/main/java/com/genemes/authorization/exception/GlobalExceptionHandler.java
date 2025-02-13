package com.genemes.authorization.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Map<String, Object> getErrorResponse(String errorType, String errorMesage) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("error", errorType);
        errorResponse.put("message", errorMesage);
        return errorResponse;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException exception) {
        Map<String, Object> errorResponse = getErrorResponse("Validation Error", exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BlockListException.class)
    public ResponseEntity<Map<String, Object>> handleBlockListException(BlockListException exception) {
        Map<String, Object> errorResponse = getErrorResponse("User in blocklist", exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
