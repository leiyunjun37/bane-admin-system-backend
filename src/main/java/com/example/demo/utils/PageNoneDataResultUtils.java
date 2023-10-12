package com.example.demo.utils;

public class PageNoneDataResultUtils {

    public static PageNoneDataResult<Object> success() {
        PageNoneDataResult<Object> pageResult = new PageNoneDataResult<>();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        return pageResult;
    }

    public static PageNoneDataResult<Object> fail() {
        PageNoneDataResult<Object> pageResult = new PageNoneDataResult<>();
        pageResult.setCode(11111);
        pageResult.setSuccess(false);
        return pageResult;
    }
}
