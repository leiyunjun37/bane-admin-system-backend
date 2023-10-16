package com.example.demo.utils.RequestBody.Products;

import lombok.Data;

@Data
public class ProductsInsertObject {

    private String name;

    private Integer price;

    private Integer inventory;

    private  String comment;
}
