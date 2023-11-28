package com.example.demo.utils.RequestBody.Users;


import lombok.Data;

@Data
public class PageLogoutResult<T> {

    private Integer code;

    private Boolean success;

    private String message;

}
