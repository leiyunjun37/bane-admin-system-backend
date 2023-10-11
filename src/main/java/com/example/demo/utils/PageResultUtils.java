package com.example.demo.utils;

import com.example.demo.utils.PageResult;

public class PageResultUtils {

    public static PageResult success(Object data) {
        PageResult pageResult = new PageResult();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        pageResult.setData(data);
        return pageResult;
    }
}
