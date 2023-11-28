package com.example.demo.controller;

import com.example.demo.entity.OrderRecord;
import com.example.demo.service.IOrderRecordService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/orderRecord")
public class OrderRecordController {

    @Autowired
    private IOrderRecordService orderRecordService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("name") String name,
                                         @RequestParam("page") Integer is_vipguest,
                                         @RequestParam("begintime") String begintime,
                                         @RequestParam("endtime") String endtime,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<OrderRecord> orderRecords = orderRecordService.getAllOrderRecords();
                Integer total = orderRecordService.countOrderRecord();
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", orderRecords);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = ( page - 1) * size;
                Integer end = start + size;
                HashMap<String, Object> hashMap = orderRecordService.select(name, is_vipguest, begintime, endtime);
                Integer total = (Integer) hashMap.get("total");
                List<OrderRecord> data = (List<OrderRecord>) hashMap.get("data");
                List<OrderRecord> orderRecords;
                if (total < size) {
                    orderRecords = data.subList(start, total);
                } else {
                    orderRecords = data.subList(start, end);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", orderRecords);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            }
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
