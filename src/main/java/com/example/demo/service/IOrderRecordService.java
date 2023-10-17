package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OrderRecord;

import java.util.List;

public interface IOrderRecordService extends IService<OrderRecord> {

    void sellVip(String name, String datetime, String comment);

    void sellUnVip(String datetime, String comment);

    void recharge(String guestName, String datetime, String comment);

}
