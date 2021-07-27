package com.mao.foodetector.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RegisterAddedBeforeThisException.class)
    public ResponseEntity<Object> registerAddedBeforeThisExceptionHandler(RegisterAddedBeforeThisException exception){
        MyExceptionClass myExceptionClass=new MyExceptionClass(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return  new ResponseEntity<>(myExceptionClass,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<Object> registerNotFoundExceptionHandler(RegisterNotFoundException exception){
        MyExceptionClass myExceptionClass=new MyExceptionClass(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return  new ResponseEntity<>(myExceptionClass,HttpStatus.NOT_FOUND);
    }

}
