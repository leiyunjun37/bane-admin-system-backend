package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Products;
import com.example.demo.mapper.ProductsMapper;
import com.example.demo.service.IProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public List<Products> select(String name, String price, Integer start, Integer size) {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        wrapper.gt("price", price);
        wrapper.eq("is_delete", 0);
        Page<Products> page = new Page<>(start, size);
        return productsMapper.selectPage(page, wrapper).getRecords();
    }

    @Override
    public void insert(String name, String comment, Integer price, Integer inventory) {
        Products products = new Products();
        products.setComment(comment);
        products.setInventory(inventory);
        products.setPrice(price);
        products.setName(name);
        products.setIs_delete(0);
        productsMapper.insert(products);
    }

    @Override
    public Boolean checkNameUnique(String name) {
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
    public void update(Integer id, String name, Integer price, Integer inventory, String comment) {
        Products products = new Products();
        products.setId(id);
        products.setComment(comment);
        products.setInventory(inventory);
        products.setPrice(price);
        products.setName(name);
        productsMapper.updateById(products);
    }

    @Override
    public Integer countProducts() {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return productsMapper.selectCount(wrapper);
    }
}
