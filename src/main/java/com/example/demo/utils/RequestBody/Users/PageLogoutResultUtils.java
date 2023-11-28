package com.example.demo.utils.RequestBody.Users;

public class PageLogoutResultUtils {

    public static PageLogoutResult<Object> success() {
        PageLogoutResult<Object> pageResult = new PageLogoutResult<>();
        pageResult.setCode(10000);
        pageResult.setSuccess(true);
        pageResult.setMessage("logout");
        return pageResult;
    }

    public static PageLogoutResult<Object> fail() {
        PageLogoutResult<Object> pageResult = new PageLogoutResult<>();
        pageResult.setCode(11111);
        pageResult.setSuccess(false);
        return pageResult;
    }

}
