package com.example.demo.utils;

import lombok.Data;

@Data
public class PageResult<T> {

    private Integer code;

    private Boolean success;

    private T data;

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
