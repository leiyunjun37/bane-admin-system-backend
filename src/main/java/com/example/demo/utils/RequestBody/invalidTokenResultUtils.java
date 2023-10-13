package com.example.demo.utils.RequestBody;

public class invalidTokenResultUtils {

    public static invalidTokenResult<Object> error(int code, boolean success, String message) {
        invalidTokenResult<Object> result = new invalidTokenResult<>();
        result.setCode(code);
        result.setSuccess(success);
        result.setMessage(message);
        return result;
    }
}
