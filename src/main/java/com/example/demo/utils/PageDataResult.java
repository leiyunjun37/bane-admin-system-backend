package com.example.demo.utils;

import lombok.Data;

@Data
public class PageDataResult<T> {

    private Integer code;

    private Boolean success;

    private T data;

    private Integer total;

    private String message;
}
