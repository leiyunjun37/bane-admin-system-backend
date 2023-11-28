package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Products;
import com.example.demo.mapper.ProductsMapper;
import com.example.demo.service.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public HashMap<String, Object> select(String name) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("create_time");
        List<Products> products = productsMapper.selectList(wrapper);
        Integer total = productsMapper.selectCount(wrapper);
        hashMap.put("data", products);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public void insert(String name, String comment, Integer price, Integer inventory, String create_time) {
        Products products = new Products();
        if (comment.isEmpty()) {
            products.setComment("-");
        } else {
            products.setComment(comment);
        }
        products.setInventory(inventory);
        products.setPrice(price);
        products.setName(name);
        products.setIs_delete(0);
        products.setCreate_time(create_time);
        productsMapper.insert(products);
    }

    @Override
    public Boolean checkNameUnique(String name, Integer id) {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        List<Products> products = productsMapper.selectList(wrapper);
        boolean result = true;
        for(Products product : products) {
            if (product.getName().equals(name)) {
                if (!product.getId().equals(id)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Boolean checkInsertNameUnique(String name) {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.eq("is_delete", 0);
        Products exist = productsMapper.selectOne(wrapper);
        return exist == null;
    }

    @Override
    public void delete(Integer id) {
        Products products = new Products();
        products.setId(id);
        products.setIs_delete(1);
        productsMapper.updateById(products);
    }

    @Override
    public void update(Integer id, String name, Integer price, Integer inventory, String comment, String update_time) {
        Products products = new Products();
        products.setId(id);
        if (comment.isEmpty()) {
            products.setComment("-");
        } else {
            products.setComment(comment);
        }
        products.setInventory(inventory);
        products.setPrice(price);
        products.setName(name);
        products.setUpdate_time(update_time);
        productsMapper.updateById(products);
    }

    @Override
    public Integer countProducts() {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return productsMapper.selectCount(wrapper);
    }

    @Override
    public List<Products> getAllProducts() {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return productsMapper.selectList(wrapper);
    }
}
