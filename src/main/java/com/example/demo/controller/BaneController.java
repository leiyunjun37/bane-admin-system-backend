package com.example.demo.controller;


import com.example.demo.service.IOperationLogService;
import com.example.demo.service.IOrderRecordService;
import com.example.demo.service.IVipGuestsService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.Bane.CountsReturnObject;
import com.example.demo.utils.RequestBody.Bane.RechargeObject;
import com.example.demo.utils.RequestBody.Bane.SellObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/bane")
public class BaneController {

    @Autowired
    private IVipGuestsService vipGuestsService;

    @Autowired
    private IOrderRecordService orderRecordService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IOperationLogService operationLogService;

    @PostMapping("/sellVip")
    public PageNoneDataResult<Object> sellVip(@RequestBody SellObject requestBody, HttpServletRequest request) {
        try {
            String guestName = requestBody.getGuestName();
            Integer endPrice = requestBody.getEndPrice();
            if (vipGuestsService.checkBalance(guestName, endPrice)) {
                vipGuestsService.sell(guestName, endPrice);
                List<String> productNames = requestBody.getProductNames();
                Integer productsPrice = requestBody.getProductsPrice();
                Integer petServePrice = requestBody.getPetServePrice();
                Integer discount = requestBody.getDiscount();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                String lastShop = String.format("于%s购买了价值%d元的宠物商品以及享受了价值%d元的宠物服务项目,享受%d折的会员折扣,总计扣费%d元", formattedDateTime, productsPrice, petServePrice, discount, endPrice);
                vipGuestsService.updateLastShop(guestName, lastShop);
                StringBuilder base = new StringBuilder("购买了宠物商品:");
                for (String product : productNames) {
                    base.append(product).append(":");
                }
                String Base = base.toString();
                String comment = String.format("于%s%s价值%d元以及享受了价值%d元的宠物服务项目,享受%d折的会员折扣,总计扣费%d元",formattedDateTime, Base, productsPrice, petServePrice,discount, endPrice);
                orderRecordService.sellVip(guestName, formattedDateTime, comment, endPrice);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("sell success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                operationLogService.insert(username, "编辑", "会员信息", formattedDateTime);
                operationLogService.insert(username, "会员消费", "会员消费", formattedDateTime);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("Insufficient balance");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/sellUnVip")
    public PageNoneDataResult<Object> sellUnVip(@RequestBody SellObject requestBody, HttpServletRequest request) {
        try {
            Integer endPrice = requestBody.getEndPrice();
            List<String> productNames = requestBody.getProductNames();
            Integer productsPrice = requestBody.getProductsPrice();
            Integer petServePrice = requestBody.getPetServePrice();
            Integer discount = requestBody.getDiscount();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            StringBuilder base = new StringBuilder("购买了宠物商品:");
            for (String product : productNames) {
                base.append(product).append(":");
            }
            String Base = base.toString();
            String comment = String.format("于%s%s价值%d元以及享受了价值%d元的宠物服务项目,享受%d折,总计消费%d元",formattedDateTime, Base, productsPrice, petServePrice, discount, endPrice);
            orderRecordService.sellUnVip(formattedDateTime, comment, endPrice);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("sell success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            operationLogService.insert(username, "普通用户消费", "普通用户消费", formattedDateTime);
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/recharge")
    public PageNoneDataResult<Object> recharge(@RequestBody RechargeObject requestBody, HttpServletRequest request) {
        try {
            String guestName = requestBody.getGuestName();
            Integer rechargeNum = requestBody.getRechargeNum();
            vipGuestsService.recharge(guestName, rechargeNum);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            String comment = String.format("于%s充值%d元", formattedDateTime, rechargeNum);
            orderRecordService.recharge(guestName, formattedDateTime, comment, rechargeNum);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("recharge success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            operationLogService.insert(username, "充值", "充值", formattedDateTime);
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @GetMapping("/counts")
    public PageDataResult<Object> counts(@RequestParam("type") Integer type) {
        try {
            CountsReturnObject countsReturnObject = orderRecordService.counts(type);
            PageDataResult<Object> result = PageDataResultUtils.success(countsReturnObject, null);
            result.setMessage("counts success");
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
