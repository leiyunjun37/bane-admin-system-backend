package com.example.demo.utils.RequestBody.Products;

import lombok.Data;

@Data
public class ProductsUpdateObject {

    private Integer id;

    private String name;

    private Integer price;

    private String comment;

    private Integer inventory;
}
