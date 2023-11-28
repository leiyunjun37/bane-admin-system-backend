package com.example.demo.utils;

import java.util.HashMap;

public class PageDataResultUtils {

    public static PageDataResult<Object> success(HashMap<String, Object> data) {
        PageDataResult<Object> pageResult = new PageDataResult<>();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        pageResult.setData(data);
        return pageResult;
    }

    public static PageDataResult<Object> fail() {
        PageDataResult<Object> pageResult = new PageDataResult<>();
        pageResult.setCode(11111);
        pageResult.setSuccess(false);
        pageResult.setData(null);
        return pageResult;
    }
}
