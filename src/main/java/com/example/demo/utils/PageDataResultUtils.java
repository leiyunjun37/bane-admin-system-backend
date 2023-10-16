package com.example.demo.utils;

public class PageDataResultUtils {

    public static PageDataResult<Object> success(Object data) {
        PageDataResult<Object> pageResult = new PageDataResult<>();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        pageResult.setData(data);
        return pageResult;
    }

    public static PageDataResult<Object> fail() {
        PageDataResult<Object> pageResult = new PageDataResult<>();
        pageResult.setCode(11111);
        pageResult.setSuccess(true);
        pageResult.setData(null);
        return pageResult;
    }
}
