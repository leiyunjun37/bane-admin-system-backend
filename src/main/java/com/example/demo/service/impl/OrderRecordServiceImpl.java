package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OrderRecord;
import com.example.demo.mapper.OrderRecordMapper;
import com.example.demo.service.IOrderRecordService;
import com.example.demo.utils.RequestBody.Bane.CountsReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements IOrderRecordService {

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Override
    public void sellVip(String name, String datetime, String comment, Integer value) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setComment(comment);
        orderRecord.setDatetime(datetime);
        orderRecord.setIs_vipguest(1);
        orderRecord.setIs_delete(0);
        orderRecord.setName(name);
        orderRecord.setValue(value);
        orderRecordMapper.insert(orderRecord);
    }

    @Override
    public void sellUnVip(String datetime, String comment, Integer value) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setComment(comment);
        orderRecord.setDatetime(datetime);
        orderRecord.setIs_vipguest(0);
        orderRecord.setIs_delete(0);
        orderRecord.setName("普通用户");
        orderRecord.setValue(value);
        orderRecordMapper.insert(orderRecord);
    }

    @Override
    public void recharge(String guestName, String datetime, String comment, Integer value) {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setComment(comment);
        orderRecord.setDatetime(datetime);
        orderRecord.setIs_vipguest(1);
        orderRecord.setIs_delete(0);
        orderRecord.setName(guestName);
        orderRecord.setValue(value);
        orderRecordMapper.insert(orderRecord);
    }

    @Override
    public CountsReturnObject counts(Integer type) {
        LocalDate now = LocalDate.now();
        CountsReturnObject countsReturnObject = new CountsReturnObject();
        List<Integer> valueList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        if (type == 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 0; i < 7; i++) {
                String formattedDate = now.minusDays(i).format(formatter);
                dateList.add(formattedDate);
                QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
                wrapper.like("datetime", formattedDate);
                wrapper.like("is_delete", 0);
                List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
                valueList.add(calculateValueList(orderRecords));
            }
        } else if (type == 1) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            for (int i = 1; i <= 12; i++){
                dateList.add(now.format(formatter));
                QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
                wrapper.like("datetime", now.format(formatter));
                wrapper.eq("is_delete", 0);
                List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
                valueList.add(calculateValueList(orderRecords));
                now = now.minusMonths(1);
            }
        } else {
            DateTimeFormatter formatter_year = DateTimeFormatter.ofPattern("yyyy");
            for (int i = 0; i < 5; i++) {
                String year = now.minus(Period.ofYears(i)).format(formatter_year);
                dateList.add(year);
                QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
                wrapper.like("datetime", year);
                wrapper.eq("is_delete", 0);
                List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
                valueList.add(calculateValueList(orderRecords));
            }
        }
        countsReturnObject.setDate(dateList);
        countsReturnObject.setValue(valueList);
        return countsReturnObject;
    }

    @Override
    public HashMap<String, Object> select(String name, Integer is_vipguest, String begintime, String endtime) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        wrapper.eq("is_vipguest", is_vipguest);
        wrapper.between("datetime", begintime, endtime);
        wrapper.eq("is_delete", 0);
        List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
        Integer total = orderRecordMapper.selectCount(wrapper);
        hashMap.put("data", orderRecords);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public Integer countOrderRecord() {
        QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return orderRecordMapper.selectCount(wrapper);
    }

    @Override
    public List<OrderRecord> getAllOrderRecords() {
        QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return orderRecordMapper.selectList(wrapper);
    }

    private Integer calculateValueList(List<OrderRecord> orderRecords) {
        return orderRecords.stream()
                .mapToInt(OrderRecord::getValue)
                .sum();
    }
}
