package com.example.demo.utils.RequestBody.Users;


import lombok.Data;

@Data
public class PageLoginResult<T> {

    private String username;

    private String password;

    private Integer code;

    private Boolean success;

    private String token;

    private String message;

}
