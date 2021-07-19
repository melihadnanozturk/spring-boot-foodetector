package com.mao.foodetector.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterAddedBeforeThisException extends  RuntimeException{
    public RegisterAddedBeforeThisException(String message,HttpStatus status){
        super(message);
    }
}
