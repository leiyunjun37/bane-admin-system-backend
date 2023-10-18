package com.example.demo.controller;

import com.example.demo.entity.Products;
import com.example.demo.service.IOperationLogService;
import com.example.demo.service.IProductsService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.Products.ProductsInsertObject;
import com.example.demo.utils.RequestBody.Products.ProductsUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private IProductsService productsService;

    @Autowired
    private IOperationLogService operationLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static String pageInfo = "商品信息";

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("name") String name,
                                         @RequestParam("price") String price,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size,
                                         HttpServletRequest request) {
        try {
            Integer start = (page - 1) * size;
            List<Products> products = productsService.select(name, price, start, size);
            Integer total = productsService.countProducts();
            PageDataResult<Object> result = PageDataResultUtils.success(products, total);
            result.setMessage("select success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String type = "查询";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            operationLogService.insert(username, type, pageInfo, formattedDateTime);
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody ProductsInsertObject requestBody, HttpServletRequest request) {
        try {
            String name = requestBody.getName();
            if (productsService.checkNameUnique(name)) {
                String comment =  requestBody.getComment();
                Integer inventory = requestBody.getInventory();
                Integer price = requestBody.getPrice();
                productsService.insert(name, comment, price, inventory);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "新增";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
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
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        try {
            productsService.delete(id);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String type = "删除";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            operationLogService.insert(username, type, pageInfo, formattedDateTime);
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody ProductsUpdateObject requestBody, HttpServletRequest request) {
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
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "编辑";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
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
