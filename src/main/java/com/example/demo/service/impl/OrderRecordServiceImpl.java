package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (type == 0) {
            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                String formattedDate = currentDate.minusDays(i).format(formatter);
                dateList.add(formattedDate);
            }
            CountsReturnObject countsReturnObject = new CountsReturnObject();
            countsReturnObject.setDate(dateList);
            List<Integer> valueList = new ArrayList<>();
            for (String date : dateList) {
                QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
                wrapper.like("datetime", date);
                wrapper.like("is_delete", 0);
                List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
                if (orderRecords.isEmpty()) {
                    valueList.add(0);
                } else {
                    int sum = 0;
                    for (OrderRecord orderRecord : orderRecords) {
                        sum += orderRecord.getValue();
                    }
                    valueList.add(sum);
                }
            }
            countsReturnObject.setValue(valueList);
            return countsReturnObject;
        } else if (type == 1) {
            List<Integer> valueList = new ArrayList<>();
            List<String> dateList = new ArrayList<>();
            for (int i = 1; i <= 12; i++){
                int year = LocalDate.now().getYear();
                String month = "";
                if (i < 10) {
                    month = String.format("%d-0%d", year, i);
                    dateList.add(month);
                } else {
                    month = String.format("%d-%d", year, i);
                    dateList.add(month);
                }
                QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
                wrapper.like("datetime", month);
                wrapper.eq("is_delete", 0);
                List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
                if (orderRecords.isEmpty()) {
                    valueList.add(0);
                } else {
                    int sum = 0;
                    for (OrderRecord orderRecord : orderRecords) {
                        sum += orderRecord.getValue();
                    }
                    valueList.add(sum);
                }
            }
            CountsReturnObject countsReturnObject = new CountsReturnObject();
            countsReturnObject.setDate(dateList);
            countsReturnObject.setValue(valueList);
            return countsReturnObject;
        } else {
            List<Integer> valueList = new ArrayList<>();
            List<String> dateList = new ArrayList<>();
            List<LocalDate> dates = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                dates.add(LocalDate.now().minus(Period.ofYears(i)));
            }
            DateTimeFormatter formatter_year = DateTimeFormatter.ofPattern("yyyy");
            for (LocalDate date : dates) {
                String year = date.format(formatter_year);
                dateList.add(year);
            }
            for (String date: dateList) {
                QueryWrapper<OrderRecord> wrapper = new QueryWrapper<>();
                wrapper.like("datetime", date);
                wrapper.eq("is_delete", 0);
                List<OrderRecord> orderRecords = orderRecordMapper.selectList(wrapper);
                if (orderRecords.isEmpty()) {
                    valueList.add(0);
                } else {
                    int sum = 0;
                    for (OrderRecord orderRecord : orderRecords) {
                        sum += orderRecord.getValue();
                    }
                    valueList.add(sum);
                }
            }
            CountsReturnObject countsReturnObject = new CountsReturnObject();
            countsReturnObject.setDate(dateList);
            countsReturnObject.setValue(valueList);
            return countsReturnObject;
        }
    }
}
