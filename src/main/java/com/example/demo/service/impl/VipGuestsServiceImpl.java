package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.VipGuests;
import com.example.demo.mapper.VipGuestsMapper;
import com.example.demo.service.IVipGuestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class VipGuestsServiceImpl extends ServiceImpl<VipGuestsMapper, VipGuests> implements IVipGuestsService {

    @Autowired
    private VipGuestsMapper vipGuestsMapper;

    @Override
    public HashMap<String, Object> getVipGuests(String name, String begintime, String endtime) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("registertime");
        wrapper.like("name", name);
        if (!Objects.equals(begintime, "") && !Objects.equals(endtime, "")) {
            wrapper.between("registertime", begintime, endtime);
        }
        wrapper.eq("is_delete", 0);
        Integer total = vipGuestsMapper.selectCount(wrapper);
        List<VipGuests> vipGuests = vipGuestsMapper.selectList(wrapper);
        System.out.println(vipGuests);
        hashMap.put("data", vipGuests);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public List<VipGuests> getAllVipGuests() {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return vipGuestsMapper.selectList(wrapper);
    }

    @Override
    public Boolean insertVipGuests(String name, String registertime, String conway, Integer balance) {
        VipGuests vipGuests = new VipGuests();
        vipGuests.setBalance(balance);
        vipGuests.setConway(conway);
        vipGuests.setIs_delete(0);
        vipGuests.setLastshop("-");
        vipGuests.setName(name);
        vipGuests.setRegistertime(registertime);
        vipGuestsMapper.insert(vipGuests);
        return true;
    }

    @Override
    public Boolean checkUnqiue(String col, Object value, Integer id){
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq(col, value);
        wrapper.eq("is_delete", 0);
        VipGuests vipGuests = vipGuestsMapper.selectOne(wrapper);
        if (vipGuests == null) {
            return true;
        } else return vipGuests.getId().equals(id);
    }

    @Override
    public Boolean checkInsertUnqiue(String col, Object value) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq(col, value);
        wrapper.eq("is_delete", 0);
        VipGuests exist = vipGuestsMapper.selectOne(wrapper);
        return exist == null;
    }

    @Override
    public String updateIsDelete(Integer id) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        VipGuests vipGuests = vipGuestsMapper.selectOne(wrapper);
        String owner = vipGuests.getName();
        VipGuests vipGuest = new VipGuests();
        vipGuest.setIs_delete(1);
        vipGuestsMapper.update(vipGuest, wrapper);
        return owner;
    }

    @Override
    public Boolean update(Integer id, String conway, String name) {
        VipGuests vipGuests = new VipGuests();
        vipGuests.setId(id);
        vipGuests.setConway(conway);
        vipGuests.setName(name);
        int rows = vipGuestsMapper.updateById(vipGuests);
        return rows > 0;
    }

    @Override
    public Integer countVipGuest() {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return vipGuestsMapper.selectCount(wrapper);
    }

    @Override
    public Boolean checkBalance(String guestName, Integer endPrice) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("name", guestName);
        wrapper.eq("is_delete", 0);
        VipGuests vipGuests = vipGuestsMapper.selectOne(wrapper);
        Integer balance = vipGuests.getBalance();
        if (balance >= endPrice) return true;
        else return null;
    }

    @Override
    public void sell(String guestName, Integer endPrice) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("name", guestName);
        wrapper.eq("is_delete", 0);
        VipGuests vipGuests = vipGuestsMapper.selectOne(wrapper);
        Integer balance = vipGuests.getBalance();
        Integer newBalance = balance - endPrice;
        VipGuests newVipGuest = new VipGuests();
        newVipGuest.setBalance(newBalance);
        vipGuestsMapper.update(newVipGuest, wrapper);
    }

    @Override
    public void updateLastShop(String guestName, String lastShop) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("name", guestName);
        wrapper.eq("is_delete", 0);
        VipGuests newVipGuest = new VipGuests();
        newVipGuest.setLastshop(lastShop);
        vipGuestsMapper.update(newVipGuest, wrapper);
    }

    @Override
    public void recharge(String guestName, Integer rechargeNum) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("name", guestName);
        wrapper.eq("is_delete", 0);
        VipGuests vipGuests = vipGuestsMapper.selectOne(wrapper);
        Integer balance = vipGuests.getBalance();
        Integer newBalance = balance + rechargeNum;
        VipGuests newVipGuest = new VipGuests();
        newVipGuest.setBalance(newBalance);
        vipGuestsMapper.update(newVipGuest, wrapper);
    }
}
