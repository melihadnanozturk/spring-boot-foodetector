package com.mao.foodetector.exeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@Getter
@Setter
@AllArgsConstructor
public class MyExceptionClass {
    private Throwable throwable;
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime time;

}
