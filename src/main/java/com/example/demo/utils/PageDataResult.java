package com.example.demo.utils;

import lombok.Data;

import java.util.HashMap;

@Data
public class PageDataResult<T> {

    private Integer code;

    private Boolean success;

    private HashMap<String, Object> data;

    private String message;
}
