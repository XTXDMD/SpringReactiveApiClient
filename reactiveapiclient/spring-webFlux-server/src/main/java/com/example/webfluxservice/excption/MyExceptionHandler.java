package com.example.webfluxservice.excption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @author zhy
 * @since 2021/3/23 16:20
 */
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleBindException(WebExchangeBindException e){
        return new ResponseEntity<String>(toStr(e),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handleBindException(CheckException e){
        return new ResponseEntity<String>(toStr(e),HttpStatus.BAD_REQUEST);
    }

    private String toStr(WebExchangeBindException e) {
        return e.getFieldErrors().stream()
                .map(err -> err.getField() + ":" + err.getDefaultMessage())
                .reduce("",(s1,s2) ->
                    s1 + "\n" + s2
                );
    }

    private String toStr(CheckException e) {
        return e.getFieldName() + ":" + e.getFieldValue();
    }
}
