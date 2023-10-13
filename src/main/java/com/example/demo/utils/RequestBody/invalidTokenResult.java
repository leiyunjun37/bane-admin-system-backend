package com.example.demo.utils.RequestBody;

import lombok.Data;

@Data
public class invalidTokenResult<T> {
        private int code;

        private boolean success;

        private String message;

}
