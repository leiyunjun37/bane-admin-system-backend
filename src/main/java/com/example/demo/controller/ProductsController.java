package com.example.demo.controller;

import com.example.demo.entity.Products;
import com.example.demo.service.IProductsService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import com.example.demo.utils.PageNoneDataResult;
import com.example.demo.utils.PageNoneDataResultUtils;
import com.example.demo.utils.RequestBody.Products.ProductsInsertObject;
import com.example.demo.utils.RequestBody.Products.ProductsUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductsService productsService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("name") String name,
                                 @RequestParam("price") String price,
                                 @RequestParam("page") Integer page,
                                 @RequestParam("size") Integer size) {
        try {
            Integer start = (page - 1) * size;
            List<Products> products = productsService.select(name, price, start, size);
            Integer total = productsService.countProducts();
            PageDataResult<Object> result = PageDataResultUtils.success(products, total);
            result.setMessage("select success");
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody ProductsInsertObject requestBody) {
        try {
            String name = requestBody.getName();
            if (productsService.checkNameUnique(name)) {
                String comment =  requestBody.getComment();
                Integer inventory = requestBody.getInventory();
                Integer price = requestBody.getPrice();
                productsService.insert(name, comment, price, inventory);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("insert failed, this name already exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @DeleteMapping("/delete")
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id) {
        try {
            productsService.delete(id);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody ProductsUpdateObject requestBody) {
        try {
            String name = requestBody.getName();
            if (productsService.checkNameUnique(name)) {
                Integer id = requestBody.getId();
                Integer price = requestBody.getPrice();
                Integer inventory = requestBody.getInventory();
                String comment = requestBody.getComment();
                productsService.update(id, name, price, inventory, comment);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("udpate success");
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("update failded, this name already exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
