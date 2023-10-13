package com.example.demo.utils.RequestBody.Users;

public class PageLoginResultUtils {

    public static PageLoginResult<Object> success() {
        PageLoginResult<Object> pageResult = new PageLoginResult<>();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        pageResult.setMessage("login success");
        return pageResult;
    }

    public static PageLoginResult<Object> fail() {
        PageLoginResult<Object> pageResult = new PageLoginResult<>();
        pageResult.setCode(11111);
        pageResult.setSuccess(false);
        pageResult.setMessage("login failed");
        return pageResult;
    }

}
