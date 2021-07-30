package com.mao.foodetector.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RegisterAddedBeforeThisException.class)
    public ResponseEntity<Object> registerAddedBeforeThisExceptionHandler(RegisterAddedBeforeThisException exception) {
        MyExceptionClass myExceptionClass = new MyExceptionClass(
                exception.getCause(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(myExceptionClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<Object> registerNotFoundExceptionHandler(RegisterNotFoundException exception) {
        MyExceptionClass myExceptionClass = new MyExceptionClass(
                exception.getCause(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(myExceptionClass, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
