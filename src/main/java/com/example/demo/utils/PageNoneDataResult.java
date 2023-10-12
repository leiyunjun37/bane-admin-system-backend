package com.example.demo.utils;

import lombok.Data;

@Data
public class PageNoneDataResult<T> {

    private Integer code;

    private Boolean success;

    private String message;
}

