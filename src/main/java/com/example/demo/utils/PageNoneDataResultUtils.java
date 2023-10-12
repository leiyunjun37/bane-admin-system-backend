package com.example.demo.utils;

public class PageNoneDataResultUtils {

    public static PageNoneDataResult success() {
        PageNoneDataResult pageResult = new PageNoneDataResult<>();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        return pageResult;
    }
}
