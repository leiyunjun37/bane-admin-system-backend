package com.example.demo.controller;


import com.example.demo.service.IOrderRecordService;
import com.example.demo.service.IVipGuestsService;
import com.example.demo.utils.PageNoneDataResult;
import com.example.demo.utils.PageNoneDataResultUtils;
import com.example.demo.utils.RequestBody.Bane.RechargeObject;
import com.example.demo.utils.RequestBody.Bane.SellObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/bane")
public class BaneController {

    @Autowired
    private IVipGuestsService vipGuestsService;

    @Autowired
    private IOrderRecordService orderRecordService;

    @PostMapping("/sellVip")
    public PageNoneDataResult<Object> sellVip(@RequestBody SellObject requestBody) {
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                String lastShop = String.format("于%s购买了价值%d的宠物商品以及享受了价值%d的宠物服务项目,享受%d折的会员折扣,总计扣费%d", formattedDateTime, productsPrice, petServePrice, discount, endPrice);
                vipGuestsService.updateLastShop(guestName, lastShop);
                StringBuilder base = new StringBuilder("购买了");
                for (String product : productNames) {
                    base.append(product).append(":");
                }
                String Base = base.toString();
                String comment = String.format("于%s%s的宠物商品,价值%d以及享受了价值%d的宠物服务项目,享受%d折的会员折扣,总计扣费%d",formattedDateTime, Base, productsPrice, petServePrice,discount, endPrice);
                orderRecordService.sellVip(guestName, formattedDateTime, comment);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("sell success");
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
    public PageNoneDataResult<Object> sellUnVip(@RequestBody SellObject requestBody) {
        try {
            Integer endPrice = requestBody.getEndPrice();
            List<String> productNames = requestBody.getProductNames();
            Integer productsPrice = requestBody.getProductsPrice();
            Integer petServePrice = requestBody.getPetServePrice();
            Integer discount = requestBody.getDiscount();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            StringBuilder base = new StringBuilder("购买了");
            for (String product : productNames) {
                base.append(product).append(":");
            }
            String Base = base.toString();
            String comment = String.format("于%s%s的宠物商品,价值%d以及享受了价值%d的宠物服务项目,享受%d折,总计消费%d",formattedDateTime, Base, productsPrice, petServePrice, discount, endPrice);
            orderRecordService.sellUnVip(formattedDateTime, comment);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("sell success");
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/recharge")
    public PageNoneDataResult<Object> recharge(@RequestBody RechargeObject requestBody) {
        try {
            String guestName = requestBody.getGuestName();
            Integer rechargeNum = requestBody.getRechargeNum();
            vipGuestsService.recharge(guestName, rechargeNum);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            String comment = String.format("于%s充值%d元", formattedDateTime, rechargeNum);
            orderRecordService.recharge(guestName, formattedDateTime, comment);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("充值成功");
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
