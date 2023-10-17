package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OrderRecord;
import com.example.demo.mapper.OrderRecordMapper;
import com.example.demo.service.IOrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements IOrderRecordService {

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Override
    public void sellVip(String name, String datetime, String comment) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setComment(comment);
        orderRecord.setDatetime(datetime);
        orderRecord.setIs_vipguest(1);
        orderRecord.setIs_delete(0);
        orderRecord.setName(name);
        orderRecordMapper.insert(orderRecord);
    }

    @Override
    public void sellUnVip(String datetime, String comment) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setComment(comment);
        orderRecord.setDatetime(datetime);
        orderRecord.setIs_vipguest(0);
        orderRecord.setIs_delete(0);
        orderRecord.setName("普通用户");
        orderRecordMapper.insert(orderRecord);
    }

    @Override
    public void recharge(String guestName, String datetime, String comment) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setComment(comment);
        orderRecord.setDatetime(datetime);
        orderRecord.setIs_vipguest(1);
        orderRecord.setIs_delete(0);
        orderRecord.setName(guestName);
        orderRecordMapper.insert(orderRecord);
    }
}
