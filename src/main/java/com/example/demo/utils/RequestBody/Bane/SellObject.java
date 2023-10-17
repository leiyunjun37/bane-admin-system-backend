package com.example.demo.utils.RequestBody.Bane;

import lombok.Data;

import java.util.List;

@Data
public class SellObject {

    private String guestName;

    private List<String> productNames;

    private Integer productsPrice;

    private Integer petServePrice;

    private Integer discount;

    private Integer endPrice;
}
