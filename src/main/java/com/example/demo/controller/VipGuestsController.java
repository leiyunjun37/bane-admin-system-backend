package com.example.demo.controller;

import com.example.demo.entity.VipGuests;
import com.example.demo.service.IVipGuestsService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.PageResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
