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
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime time;

}
