package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OrderRecord;
import com.example.demo.utils.RequestBody.Bane.CountsReturnObject;

import java.util.HashMap;
import java.util.List;

public interface IOrderRecordService extends IService<OrderRecord> {

    void sellVip(String name, String datetime, String comment, Integer value);

    void sellUnVip(String datetime, String comment, Integer value);

    void recharge(String guestName, String datetime, String comment, Integer value);

    CountsReturnObject counts(Integer type);

    HashMap<String, Object> select(String name, Integer is_vipguest, String begintime, String endtime, Integer start, Integer size);

    Integer countOrderRecord();

    List<OrderRecord> getAllOrderRecords();

}
