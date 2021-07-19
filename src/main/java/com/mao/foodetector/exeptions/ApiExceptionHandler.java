package com.mao.foodetector.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RegisterAddedBeforeThisException.class)
    public ResponseEntity<?> registerAddedBeforeThisExceptionHandler(RegisterAddedBeforeThisException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<?> registerNotFoundExceptionHandler(RegisterNotFoundException exception){
        return  new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

}
