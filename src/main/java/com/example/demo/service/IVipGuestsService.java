package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.VipGuests;

import java.util.HashMap;
import java.util.List;

public interface IVipGuestsService extends IService<VipGuests> {
    HashMap<String, Object> getVipGuests(String name, String begintime, String endtime, Integer start, Integer size);

    List<VipGuests> getAllVipGuests();

    Boolean insertVipGuests(String name, String registertime, String conway, Integer balance);

    Boolean checkUnqiue(String col, Object value);

    String updateIsDelete(Integer id);

    Boolean update(Integer id, String conway, String name);

    Integer countVipGuest();

    Boolean checkBalance(String guestName, Integer endPrice);

    void sell(String guestName, Integer endPrice);

    void updateLastShop(String guestName, String lastShop);

    void recharge(String guestName, Integer rechargeNum);
}
