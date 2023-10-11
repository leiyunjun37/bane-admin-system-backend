package com.example.demo.utils;

import lombok.Data;

@Data
public class PageResult<T> {

    private Integer code;

    private Boolean success;

    private T data;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
