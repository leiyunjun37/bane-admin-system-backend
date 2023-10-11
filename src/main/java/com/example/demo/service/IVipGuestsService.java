package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.VipGuests;

import java.util.List;

public interface IVipGuestsService extends IService<VipGuests> {
    List<VipGuests> getVipGuests(String name, String registertime, Integer start, Integer size);

    Boolean insertVipGuests(String name, String registertime, String conway, Integer balance);
}
