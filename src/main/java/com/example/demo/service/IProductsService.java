package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Products;

import java.util.List;

public interface IProductsService extends IService<Products> {

    List<Products> select(String name, String price, Integer start, Integer size);

    void insert(String name, String comment, Integer price, Integer inventory);

    Boolean checkNameUnique(String name);

    void delete(Integer id);

    void update(Integer id, String name, Integer price, Integer inventory, String comment);

    Integer countProducts();
}
