package com.example.demo.controller;

import com.example.demo.entity.VipGuests;
import com.example.demo.service.IVipGuestsService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.PageResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.RequestBody.Vipguests.VipguestInsertObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/vipguests")
public class VipGuestsController {

    @Autowired
    private IVipGuestsService vipGuestsService;

    @GetMapping("/get")
    public PageResult select(@RequestParam("page") Integer page,
                             @RequestParam("name") String name,
                             @RequestParam("registertime") String registertime,
                             @RequestParam("size") Integer size) {
        Integer start = (page - 1) * size + 1;
        List<VipGuests> vipGuests = vipGuestsService.getVipGuests(name, registertime, start, size);
        PageResult result = PageResultUtils.success(vipGuests);
        result.setMessage("select success");
        return result;
    }

    @PostMapping("/post")
    public PageResult insert(@RequestBody VipguestInsertObject requestBody) {
        String name = requestBody.getName();
        String conway = requestBody.getConway();
        Integer balance = requestBody.getBalance();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String registertime = currentDate.format(formatter);
        Boolean insert = vipGuestsService.insertVipGuests(name, registertime, conway, balance);
        PageResult result = PageResultUtils.success(insert);
        result.setMessage("insert success");
        return result;
    }
}
