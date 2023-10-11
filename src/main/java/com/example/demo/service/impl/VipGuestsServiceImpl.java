package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.VipGuests;
import com.example.demo.mapper.VipGuestsMapper;
import com.example.demo.service.IVipGuestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipGuestsServiceImpl extends ServiceImpl<VipGuestsMapper, VipGuests> implements IVipGuestsService {

    @Autowired
    private VipGuestsMapper vipGuestsMapper;

    @Override
    public List<VipGuests> getVipGuests(String name, String registertime, Integer start, Integer size) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        wrapper.gt("registertime", registertime);
        wrapper.eq("is_delete", 0);
        Page<VipGuests> page = new Page<>(start, size);
        return vipGuestsMapper.selectPage(page, wrapper).getRecords();
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
}
