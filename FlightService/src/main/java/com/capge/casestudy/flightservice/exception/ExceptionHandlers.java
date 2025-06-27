package com.capge.casestudy.flightservice.exception;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlers {
    @ExceptionHandler(NoFlightFoundException.class)
    public ResponseEntity<Map<String , Object>> handlerForNoFlightException(NoFlightFoundException noFlightFoundException){
          Map<String , Object> reponseMap = new HashMap<>();
		reponseMap.put("message" , noFlightFoundException.getMessage());
		reponseMap.put("status" , HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(reponseMap);
    }


    @ExceptionHandler(FormateNotAllowedException.class)
    public ResponseEntity<Map<String , Object>> handlerForFormateNotAllowedException(FormateNotAllowedException formateNotAllowedException){
	   Map<String , Object> reponseMap = new HashMap<>();
	   reponseMap.put("message" , formateNotAllowedException.getMessage());
	   reponseMap.put("status" , HttpStatus.NOT_FOUND);
	   return ResponseEntity.ok(reponseMap);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

