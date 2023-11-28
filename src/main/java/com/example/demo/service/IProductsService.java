package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Products;

import java.util.HashMap;
import java.util.List;

public interface IProductsService extends IService<Products> {

    HashMap<String, Object> select(String name);

    void insert(String name, String comment, Integer price, Integer inventory, String create_time);

    Boolean checkNameUnique(String name, Integer id);

    Boolean checkInsertNameUnique(String name);

    void delete(Integer id);

    void update(Integer id, String name, Integer price, Integer inventory, String comment, String update_time);

    Integer countProducts();

    List<Products> getAllProducts();
}
