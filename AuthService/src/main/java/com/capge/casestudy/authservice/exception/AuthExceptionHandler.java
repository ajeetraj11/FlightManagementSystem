package com.capge.casestudy.authservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(UserNotExistsExceptions.class)
    public ResponseEntity<Map<String , Object>> userNotFoundException(UserNotExistsExceptions userNotExistsExceptions)
    {
        HashMap<String , Object> responseMap = new HashMap<>();
        responseMap.put("message" , userNotExistsExceptions.getMessage());
        responseMap.put("status" , HttpStatus.NOT_FOUND);
        responseMap.put("error" ,userNotExistsExceptions.getStackTrace());
        return ResponseEntity.ok(responseMap);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicateEntry(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists. Please use a different email.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong. Please try again.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }


}