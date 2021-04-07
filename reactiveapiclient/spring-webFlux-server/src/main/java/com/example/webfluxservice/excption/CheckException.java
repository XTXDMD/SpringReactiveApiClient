package com.example.webfluxservice.excption;

import lombok.Data;

/**
 * @author zhy
 * @since 2021/3/23 17:30
 */

@Data
public class CheckException extends RuntimeException{
    private String fieldName;

    private String fieldValue;

    public CheckException(String fieldName, String fieldValue) {
        super();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
