package com.example.demo.utils;

public class PageDataResultUtils {

    public static PageDataResult success(Object data) {
        PageDataResult pageResult = new PageDataResult();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        pageResult.setData(data);
        return pageResult;
    }
}
