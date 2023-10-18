package com.example.demo.utils.RequestBody.Bane;

import lombok.Data;

import java.util.List;

@Data
public class CountsReturnObject {

    private List<String> date;

    private List<Integer> value;
}
