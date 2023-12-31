package com.example.demo.utils.RequestBody.Users;


import lombok.Data;

import java.util.HashMap;

@Data
public class PageLoginResult<T> {

    private Integer code;

    private Boolean success;

    private HashMap<String, Object> data;

    private String message;

}
