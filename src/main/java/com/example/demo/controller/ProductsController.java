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
import java.util.HashMap;

@CrossOrigin(value = "http://localhost:8080")
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
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<Products> products = productsService.getAllProducts();
                Integer total = productsService.countProducts();
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", products);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = (page - 1) * size;
                Integer end = start + size;
                HashMap<String, Object> hashMap = productsService.select(name);
                Integer total = (Integer) hashMap.get("total");
                List<Products> data = (List<Products>) hashMap.get("data");
                List<Products> products;
                if (total < size) {
                    products = data.subList(start, total);
                } else {
                    products = data.subList(start, end);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", products);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            }
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
            if (productsService.checkInsertNameUnique(name)) {
                String comment =  requestBody.getComment();
                Integer inventory = requestBody.getInventory();
                Integer price = requestBody.getPrice();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                productsService.insert(name, comment, price, inventory, formattedDateTime);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "新增";
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            Integer id = requestBody.getId();
            if (productsService.checkNameUnique(name, id)) {
                Integer price = requestBody.getPrice();
                Integer inventory = requestBody.getInventory();
                String comment = requestBody.getComment();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                productsService.update(id, name, price, inventory, comment, formattedDateTime);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("udpate success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "编辑";
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
